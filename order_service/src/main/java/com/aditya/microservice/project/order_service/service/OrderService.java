package com.aditya.microservice.project.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.aditya.microservice.project.order_service.dto.OrderLineItemDto;
import com.aditya.microservice.project.order_service.dto.OrderRequest;
import com.aditya.microservice.project.order_service.dto.StockResponse;
import com.aditya.microservice.project.order_service.model.Order;
import com.aditya.microservice.project.order_service.model.OrderLineItems;
import com.aditya.microservice.project.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();

        List<OrderLineItems> items = orderRequest.getOrderLineItems()
                .stream()
                .map(this::mapToOrderLineItems)
                .toList();

        order.setOrderLineItems(items);

        // Extract skuCodes from request DTOs
        List<String> skuCodes = orderRequest.getOrderLineItems()
                .stream()
                .map(OrderLineItemDto::getSkuCode)
                .toList();

        // Call inventory-service and get response array
        StockResponse[] response = webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("inventory-service")
                        .port(8082)
                        .path("/api/inventory")
                        .queryParam("skuCodes", skuCodes) // IMPORTANT: skuCodes (plural)
                        .build())
                .retrieve()
                .bodyToMono(StockResponse[].class)
                .block();

        if (response == null) {
            throw new RuntimeException("Inventory service returned null response");
        }

        List<StockResponse> result = List.of(response);

        boolean allInStock = result.stream().allMatch(StockResponse::isInStock);

        if (allInStock) {
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Stock is not available, please try again later");
        }
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemDto orderLineItemDto) {
        return OrderLineItems.builder()
                .skuCode(orderLineItemDto.getSkuCode())
                .productName(orderLineItemDto.getProductName())
                .quantity(orderLineItemDto.getQuantity())
                .price(orderLineItemDto.getPrice())
                .build();
    }
}
