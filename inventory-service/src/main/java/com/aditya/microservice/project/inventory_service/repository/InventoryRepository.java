package com.aditya.microservice.project.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aditya.microservice.project.inventory_service.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("SELECT i FROM t_inventory i WHERE i.skuCode = ?1")
    Inventory findBySkuCode(String skuCode);
}
