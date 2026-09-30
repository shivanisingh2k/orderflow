package com.orderflow.orderflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.orderflow.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}