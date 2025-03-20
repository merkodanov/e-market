package com.emarket.mapper;

import com.emarket.dto.ProductResponseDTO;
import com.emarket.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class ProductMapperTest {

    @Test
    void toResponseDTO() {
        Date createdAt = new Date();
        Product product = new Product("title", "description", createdAt, 1, 1);
        product.setId(1);

        Assertions.assertEquals(new ProductResponseDTO(1, "title",
                        "description", createdAt, 1, 1),
                ProductMapper.toResponseDTO(product));
    }
}