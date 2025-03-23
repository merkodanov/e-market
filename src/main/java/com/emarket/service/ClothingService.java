package com.emarket.service;

import com.emarket.model.Clothing;
import com.emarket.model.ClothingSizeColor;
import com.emarket.repository.ClothingRepository;
import com.emarket.repository.ClothingSizeColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Clothing> findByColorAndSize(String color, String size) {
        List<ClothingSizeColor> clothingSizeColorList = clothingSizeColorRepository.
                findClothingSizeColorByColorNameAndSizeName(color, size);

        return clothingSizeColorList.stream().map(ClothingSizeColor::getClothing).toList();
    }

    public List<Clothing> findAllClothes() {
        List<Clothing> clothing = new ArrayList<>();
        Iterable<Clothing> clothingIterable = clothingRepository.findAll();
        clothingIterable.forEach(clothing::add);

        return clothing;
    }

    public List<Clothing> findByColorOrSize(String color, String size) {
        List<ClothingSizeColor> clothingSizeColors = clothingSizeColorRepository
                .findClothingSizeColorByColorNameOrSizeName(color, size);

        return clothingSizeColors.stream().map(ClothingSizeColor::getClothing).toList();
    }
}
