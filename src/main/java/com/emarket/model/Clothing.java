package com.emarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Clothing extends Product {
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;
}
