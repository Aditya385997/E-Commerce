package com.aditya.microservice.project.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aditya.microservice.project.order_service.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {

}