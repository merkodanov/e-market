package com.emarket.service;

import com.emarket.model.Clothing;
import com.emarket.model.ClothingSizeColor;
import com.emarket.repository.ClothingRepository;
import com.emarket.repository.ClothingSizeColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClothingService {
    private final ClothingRepository clothingRepository;
    private final ClothingSizeColorRepository clothingSizeColorRepository;

    public Optional<Clothing> findById(long id) {
        return clothingRepository.findById(id);
    }

    public List<Clothing> findByColorAndSize(String color, String size) {
        List<ClothingSizeColor> clothingSizeColorList = clothingSizeColorRepository.
                findClothingSizeColorByColorNameAndSizeName(color, size);

        return clothingSizeColorList.stream().map(ClothingSizeColor::getClothing).collect(Collectors.toList());
    }

    public List<Clothing> findAllClothes() {
        return null;
    }
}
