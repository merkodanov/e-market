package com.emarket.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Clothing extends Product {
    public Clothing(String title, String description, float rating, float price) {
        super(title, description, rating, price);
    }

    protected Clothing() {
        super();
    }
}
