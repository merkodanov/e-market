package com.emarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clothing_image", uniqueConstraints = {@UniqueConstraint(columnNames =
        {"image_id", "clothing_size_color_id"})})
public class ClothingImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @ManyToOne
    @JoinColumn(name = "clothing_size_color_id", nullable = false)
    private ClothingSizeColor clothingSizeColor;
}
