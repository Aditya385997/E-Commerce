package com.aditya.microservice.project.inventory_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aditya.microservice.project.inventory_service.model.Inventory;
import com.aditya.microservice.project.inventory_service.repository.InventoryRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode);
        return inventory != null && inventory.getQuantity() > 0;
    }
}
