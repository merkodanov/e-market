package com.emarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ClothingReview extends ProductReview {
    @ManyToOne
    private Clothing clothing;
}
