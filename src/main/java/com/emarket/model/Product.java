package com.emarket.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private Date createdAt = new Date();
    private float rating;
    private float price;

    public Product(String title, String description, float rating, float price) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.price = price;
    }

    protected Product() {

    }
}
