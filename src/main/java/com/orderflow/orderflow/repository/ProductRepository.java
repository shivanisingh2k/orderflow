package com.orderflow.orderflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderflow.orderflow.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}