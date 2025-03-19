package com.emarket.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private Date createdAt = new Date();
    private float rating;
    private float price;

    public Product(String title, String description, Date createdAt, float rating, float price) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.rating = rating;
        this.price = price;
    }

    protected Product() {

    }
}
