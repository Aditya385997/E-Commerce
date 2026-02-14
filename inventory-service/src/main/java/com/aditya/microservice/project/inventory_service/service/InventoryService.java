package com.aditya.microservice.project.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aditya.microservice.project.inventory_service.dto.StockResponse;
import com.aditya.microservice.project.inventory_service.repository.InventoryRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<StockResponse> isInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes)
                .stream()
                .map(inventory -> StockResponse.builder()    
                        .inStock(inventory.getQuantity() > 0)
                        .skuCode(inventory.getSkuCode())
                        .build())
                .toList();
    }
}
