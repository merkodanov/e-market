package com.emarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clothing_size_color", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"clothing_id", "size_id", "color_id"})
})
public class ClothingSizeColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "clothing_id", nullable = false)
    private Clothing clothing;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
}
