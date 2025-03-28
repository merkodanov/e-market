package com.emarket.service;

import com.emarket.model.Clothing;
import com.emarket.model.ClothingSizeColor;
import com.emarket.repository.ClothingRepository;
import com.emarket.repository.ClothingSizeColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClothingService {
    private final ClothingRepository clothingRepository;
    private final ClothingSizeColorRepository clothingSizeColorRepository;

    public Optional<Clothing> findById(long id) {
        return clothingRepository.findById(id);
    }


    public Page<Clothing> findAllClothes(Pageable pageable) {
        return clothingRepository.findAll(pageable);
    }

    public Page<Clothing> findByColorAndSize(List<String> color, List<String> size, Pageable pageable) {
        Page<ClothingSizeColor> clothingSizeColorList = clothingSizeColorRepository.
                findClothingSizeColorByColorNameInAndSizeNameIn(color, size, pageable);

        return clothingSizeColorList.map(ClothingSizeColor::getClothing);
    }

    public Page<Clothing> findByColorOrSize(List<String> color, List<String> size, Pageable pageable) {
        Page<ClothingSizeColor> clothingSizeColors = clothingSizeColorRepository
                .findClothingSizeColorByColorNameInOrSizeNameIn(color, size, pageable);

        return clothingSizeColors.map(ClothingSizeColor::getClothing);
    }
}
