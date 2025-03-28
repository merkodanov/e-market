package com.emarket.repository;

import com.emarket.model.ClothingSizeColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClothingSizeColorRepository extends CrudRepository<ClothingSizeColor, Long> {
    Page<ClothingSizeColor> findClothingSizeColorByColorNameInAndSizeNameIn(List<String> color, List<String> size, Pageable pageable);

    Page<ClothingSizeColor> findClothingSizeColorByColorNameInOrSizeNameIn(List<String> color, List<String> size, Pageable pageable);

}
