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

    private int userId;

    public Product(int userId, float price, float rating, Date createdAt, String description, String title) {
        this.userId = userId;
        this.price = price;
        this.rating = rating;
        this.createdAt = createdAt;
        this.description = description;
        this.title = title;
    }

    protected Product() {

    }
}
