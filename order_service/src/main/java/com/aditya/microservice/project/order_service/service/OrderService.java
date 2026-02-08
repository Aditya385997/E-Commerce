package com.aditya.microservice.project.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aditya.microservice.project.order_service.dto.OrderLineItemDto;
import com.aditya.microservice.project.order_service.dto.OrderRequest;
import com.aditya.microservice.project.order_service.model.Order;
import com.aditya.microservice.project.order_service.model.OrderLineItems;
import com.aditya.microservice.project.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .build();
     
        List<OrderLineItems> items = orderRequest.getOrderLineItems().stream().map(this::mapToOrderLineItems).toList();
        order.setOrderLineItems(items);

        orderRepository.save(order);
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
