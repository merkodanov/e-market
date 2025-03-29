package com.emarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String imageUrl;

    private String altImage;
}
