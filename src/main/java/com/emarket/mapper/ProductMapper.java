package com.emarket.mapper;

import com.emarket.dto.ProductResponseDTO;
import com.emarket.model.Product;

public class ProductMapper {
    public static ProductResponseDTO toResponseDTO(Product product) {
        return new ProductResponseDTO(product.getId(), product.getTitle(),
                product.getDescription(), product.getCreatedAt(),
                product.getRating(), product.getPrice());
    }
}
