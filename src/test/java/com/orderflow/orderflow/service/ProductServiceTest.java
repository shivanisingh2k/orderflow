package com.orderflow.orderflow.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;

import com.orderflow.orderflow.Product;
import com.orderflow.orderflow.exception.ProductNotFoundException;
import com.orderflow.orderflow.repository.ProductRepository;
import com.orderflow.orderflow.dto.ProductResponse;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getProductById_shouldThrowException_whenProductDoesNotExist() {

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(999L)
        );
    }
    @Test
    void getProductById_shouldReturnProduct_whenProductExists() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Keyboard");
        product.setPrice(1000);
        product.setStock(8);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Keyboard", response.getName());
        assertEquals(1000, response.getPrice());
        assertEquals(8, response.getStock());
    }
}