package com.emarket.controller;

import com.emarket.model.Product;
import com.emarket.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class ProductController {
    final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public ResponseEntity<String> getProduct() {
        Product product = new Product(1, 1, 1, new Date(), "a", "a");
        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("A");
    }
}
