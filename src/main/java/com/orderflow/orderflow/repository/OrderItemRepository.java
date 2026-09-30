package com.orderflow.orderflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.orderflow.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}