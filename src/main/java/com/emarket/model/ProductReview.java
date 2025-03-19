package com.emarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String title;
    public Rating rating;
    public String body;
    public int userId;

    @ManyToOne
    public Product product;
}

