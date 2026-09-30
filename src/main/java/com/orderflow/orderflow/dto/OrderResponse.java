package com.orderflow.orderflow.dto;

import java.time.LocalDateTime;

public class OrderResponse {

    private Long id;
    private Long userId;
    private String status;
    private double totalAmount;
    private LocalDateTime createdAt;

    public OrderResponse(Long id, Long userId, String status,
                         double totalAmount, LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}