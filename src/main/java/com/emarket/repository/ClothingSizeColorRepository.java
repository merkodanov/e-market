package com.emarket.repository;

import com.emarket.model.ClothingSizeColor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClothingSizeColorRepository extends CrudRepository<ClothingSizeColor, Long> {
    List<ClothingSizeColor> findClothingSizeColorByColorNameAndSizeName(String color, String size);
}
