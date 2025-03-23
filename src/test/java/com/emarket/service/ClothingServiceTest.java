package com.emarket.service;

import com.emarket.model.Clothing;
import com.emarket.model.ClothingSizeColor;
import com.emarket.model.Color;
import com.emarket.model.Size;
import com.emarket.repository.ClothingRepository;
import com.emarket.repository.ClothingSizeColorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClothingServiceTest {
    @Mock
    private ClothingRepository clothingRepository;

    @Mock
    private ClothingSizeColorRepository clothingSizeColorRepository;

    @InjectMocks
    private ClothingService clothingService;

    @Test
    void getClothing_Is_Success() {
        long id = 1;
        Optional<Clothing> clothing = Optional.of(new Clothing("title", "description",
                1, 1));
        Mockito.when(clothingRepository.findById(id)).thenReturn(clothing);

        Optional<Clothing> actualClothing = clothingService.findById(id);

        actualClothing.ifPresent(value -> Assertions.assertEquals(clothing.get(), value));
    }

    @Test
    void getClothing_By_Color_And_Size_Is_Success() {
        String color = "color";
        String size = "size";
        List<ClothingSizeColor> clothingSizeColors = List.of(
                new ClothingSizeColor(new Clothing("title", "description", 1, 1),
                        new Size(), new Color()));
        Mockito.when(clothingSizeColorRepository.findClothingSizeColorByColorNameAndSizeName(
                color, size)).thenReturn(clothingSizeColors);

        List<Clothing> clothingList = clothingService.findByColorAndSize(color, size);

        Assertions.assertEquals(clothingList.getFirst().getTitle(),
                clothingSizeColors.getFirst().getClothing().getTitle());
    }
}