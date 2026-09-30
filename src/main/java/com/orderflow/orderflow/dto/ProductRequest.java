package com.orderflow.orderflow.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

	@NotBlank(message = "Name cannot be blank")
	private String name;

	@Min(value = 0, message = "Price must be 0 or greater")
	private double price;

	@Min(value = 0, message = "Stock must be 0 or greater")
	private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}