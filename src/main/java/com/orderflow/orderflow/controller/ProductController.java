package com.orderflow.orderflow.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import com.orderflow.orderflow.service.ProductService;
import java.util.List;
import com.orderflow.orderflow.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.orderflow.orderflow.dto.ProductRequest;
import com.orderflow.orderflow.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.data.web.PageableDefault;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
	    this.productService = productService;
	}

	@GetMapping("/products") //When someone sends a GET request to /products, execute getProducts().
	public Page<ProductResponse> getProducts(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return productService.getAllProducts(pageable);//http://localhost:8080/products- this command on browser gives this result
	}
	@PostMapping("/products")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponse createProduct(
	        @RequestBody @Valid ProductRequest productRequest) {
	    return productService.createProduct(productRequest);
	}
	@PutMapping("/products/{id}")
	public ProductResponse updateProduct(
	        @PathVariable Long id,
	        @RequestBody @Valid ProductRequest productRequest) {

	    return productService.updateProduct(id, productRequest);
	}
	@GetMapping("/products/{id}")
	public ProductResponse getProductById(@PathVariable Long id) {
	    return productService.getProductById(id);
	}
	@DeleteMapping("/products/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long id) {
	    productService.deleteProduct(id);
	}
}