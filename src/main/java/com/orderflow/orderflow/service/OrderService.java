package com.orderflow.orderflow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderflow.orderflow.Order;
import com.orderflow.orderflow.Product;
import com.orderflow.orderflow.User;
import com.orderflow.orderflow.dto.OrderItemRequest;
import com.orderflow.orderflow.dto.OrderRequest;
import com.orderflow.orderflow.dto.OrderResponse;
import com.orderflow.orderflow.exception.UserNotFoundException;
import com.orderflow.orderflow.repository.OrderItemRepository;
import com.orderflow.orderflow.repository.OrderRepository;
import com.orderflow.orderflow.repository.ProductRepository;
import com.orderflow.orderflow.repository.UserRepository;
import com.orderflow.orderflow.OrderItem;
import com.orderflow.orderflow.exception.InsufficientStockException;

import com.orderflow.orderflow.exception.ProductNotFoundException;
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + orderRequest.getUserId()
                        ));

        Order order = new Order();

        order.setUser(user);
        order.setStatus("CREATED");
        order.setTotalAmount(0);
        order.setCreatedAt(java.time.LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        double totalAmount = 0;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {

        	Product product = productRepository.findById(itemRequest.getProductId())
        	        .orElseThrow(() ->
        	                new ProductNotFoundException(
        	                        "Product not found with id: "
        	                        + itemRequest.getProductId()
        	                )
        	        );

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            totalAmount += product.getPrice() * itemRequest.getQuantity();

            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(savedOrder);

            orderItemRepository.save(orderItem);
            
            product.setStock(product.getStock() - itemRequest.getQuantity());

            productRepository.save(product);
        }

        savedOrder.setTotalAmount(totalAmount);

        orderRepository.save(savedOrder);

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getUser().getId(),
                savedOrder.getStatus(),
                savedOrder.getTotalAmount(),
                savedOrder.getCreatedAt()
        );
    }
}