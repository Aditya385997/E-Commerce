package com.aditya.microservice.project.product_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aditya.microservice.project.product_service.dto.ProductRequest;
import com.aditya.microservice.project.product_service.repository.ProductRepository;

import tools.jackson.databind.ObjectMapper;



@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@SuppressWarnings("deprecation")
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
void shouldCreateProduct() throws Exception {
    long before = productRepository.count();

    ProductRequest productRequest = getProductRequest();
    String body = objectMapper.writeValueAsString(productRequest);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/create")
            .contentType("application/json")
            .content(body))
            .andExpect(MockMvcResultMatchers.status().isCreated());

    long after = productRepository.count();
    assertEquals(before + 1, after);
}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone 12")
				.description("Rose Gold Iphone 12")
				.imageUrl("https://images.unsplash.com/photo-1617847853453-b5d5c9d3a1f2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80")
				.price(100.0)
				.build();
	}

	@Test
	void shouldGetAllProducts() throws Exception {
		long before = productRepository.count();

		ProductRequest productRequest = getProductRequest();
		String body = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/create")
				.contentType("application/json")
				.content(body))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		long after = productRepository.count();
		assertEquals(before + 1, after);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/getAll")
				.contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/getAll")
				.contentType("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk());	
	}
	
}
