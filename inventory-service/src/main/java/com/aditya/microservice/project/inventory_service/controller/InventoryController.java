package com.aditya.microservice.project.inventory_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aditya.microservice.project.inventory_service.dto.StockResponse;
import com.aditya.microservice.project.inventory_service.service.InventoryService;


import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> isInStock(@RequestParam("skuCodes") List<String> skuCodes) {
        return inventoryService.isInStock(skuCodes);
    }
}
