package com.emarket.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private Date createdAt = new Date();
    private float rating;
    private float price;
}
