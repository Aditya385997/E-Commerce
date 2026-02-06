package com.aditya.microservice.project.product_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.aditya.microservice.project.product_service.dto.ProductRequest;
import com.aditya.microservice.project.product_service.dto.ProductResponse;
import com.aditya.microservice.project.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

   @PostMapping("/create")
   @ResponseStatus(HttpStatus.CREATED)
   public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);   
   }
   @GetMapping("/getAll")
   @ResponseStatus(HttpStatus.OK)
   public List<ProductResponse> getAllProducts() {
      return productService.getAllProducts();
   }
   
}
