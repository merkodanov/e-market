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
        List<String> color = List.of("blue", "red");
        List<String> size = List.of("small", "big");
        List<ClothingSizeColor> clothingSizeColors = List.of(
                new ClothingSizeColor(new Clothing("title", "description", 1, 1),
                        new Size(), new Color()));
        Mockito.when(clothingSizeColorRepository.findClothingSizeColorByColorNameInAndSizeNameIn(
                color, size)).thenReturn(clothingSizeColors);

        List<Clothing> clothingList = clothingService.findByColorAndSize(color, size);

        Assertions.assertEquals(clothingList.getFirst().getTitle(),
                clothingSizeColors.getFirst().getClothing().getTitle());
    }

    @Test
    void getAllClothing_Is_Success() {
        Clothing clothing = new Clothing("t", "d", 1, 1);
        Mockito.when(clothingRepository.findAll()).thenReturn(List.of(clothing));

        List<Clothing> clothingList = clothingService.findAllClothes();

        Assertions.assertEquals(clothing, clothingList.getFirst());
    }

    @Test
    void getClothing_By_Size_Or_Color_Is_Success() {
        List<String> color = List.of("blue");
        List<ClothingSizeColor> clothingSizeColors = List.of(new ClothingSizeColor(
                new Clothing("t", "d", 1, 1),
                new Size(), new Color()
        ));
        Mockito.when(clothingSizeColorRepository.findClothingSizeColorByColorNameInOrSizeNameIn(color, null))
                .thenReturn(clothingSizeColors);

        List<Clothing> clothing = clothingService.findByColorOrSize(color, null);

        Assertions.assertEquals(clothingSizeColors.getFirst().getClothing(), clothing.getFirst());
    }
}