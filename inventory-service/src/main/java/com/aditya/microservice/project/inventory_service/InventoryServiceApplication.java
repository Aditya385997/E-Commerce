package com.aditya.microservice.project.inventory_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.aditya.microservice.project.inventory_service.model.Inventory;
import com.aditya.microservice.project.inventory_service.repository.InventoryRepository;




@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = Inventory.builder()
					.skuCode("Soap")
					.quantity(100)
					.build();
			inventoryRepository.save(inventory);
			Inventory inventory1 = Inventory.builder()
					.skuCode("Iphone 11")
					.quantity(0)
					.build();
			inventoryRepository.save(inventory1);
		};
	}
}