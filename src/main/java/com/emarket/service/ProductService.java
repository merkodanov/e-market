package com.emarket.service;

import com.emarket.model.Product;
import com.emarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product findProductById(long id){
        return this.productRepository.findById(id).orElseThrow();
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }
}
