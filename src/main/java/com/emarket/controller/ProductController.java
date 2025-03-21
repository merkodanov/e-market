package com.emarket.controller;

import com.emarket.dto.ProductResponseDTO;
import com.emarket.mapper.ProductMapper;
import com.emarket.model.Product;
import com.emarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable long id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toResponseDTO(product));
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveProduct(Product product){
        productService.saveProduct(product);
        return ResponseEntity.ok("Product " + product +" is saved");
    }
}
