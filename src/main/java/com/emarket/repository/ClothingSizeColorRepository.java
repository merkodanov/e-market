package com.emarket.repository;

import com.emarket.model.ClothingSizeColor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClothingSizeColorRepository extends CrudRepository<ClothingSizeColor, Long> {
    List<ClothingSizeColor> findClothingSizeColorByColorNameInAndSizeNameIn(List<String> color, List<String> size);

    List<ClothingSizeColor> findClothingSizeColorByColorNameInOrSizeNameIn(List<String> color, List<String> size);
}
