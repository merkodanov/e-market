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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        int offset = 0;
        int limit = 1;
        Pageable pageable = PageRequest.of(offset, limit);
        Page<ClothingSizeColor> clothingSizeColors = new PageImpl<>(List.of(
                new ClothingSizeColor(new Clothing("title", "description", 1, 1),
                        new Size(), new Color())));
        Mockito.when(clothingSizeColorRepository.findClothingSizeColorByColorNameInAndSizeNameIn(
                color, size, pageable)).thenReturn(clothingSizeColors);

        Page<Clothing> clothingList = clothingService.findByColorAndSize(color, size, pageable);

        Assertions.assertEquals(clothingList.getContent().getFirst().getTitle(),
                clothingSizeColors.getContent().getFirst().getClothing().getTitle());
    }

    @Test
    void getAllClothing_Is_Success() {
        Clothing clothing = new Clothing("t", "d", 1, 1);
        int offset = 0;
        int limit = 1;
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Clothing> clothingPage = new PageImpl<>(List.of(clothing));
        Mockito.when(clothingRepository.findAll(pageable)).thenReturn(clothingPage);

        Page<Clothing> clothingList = clothingService.findAllClothes(pageable);

        Assertions.assertEquals(clothing, clothingList.getContent().getFirst());
    }

    @Test
    void getClothing_By_Size_Or_Color_Is_Success() {
        List<String> color = List.of("blue");
        Page<ClothingSizeColor> clothingSizeColors = new PageImpl<>(List.of(new ClothingSizeColor(
                new Clothing("t", "d", 1, 1),
                new Size(), new Color()
        )));
        int offset = 0;
        int limit = 1;
        Pageable pageable = PageRequest.of(offset, limit);
        Mockito.when(clothingSizeColorRepository
                        .findClothingSizeColorByColorNameInOrSizeNameIn(color, null, pageable))
                .thenReturn(clothingSizeColors);

        Page<Clothing> clothing = clothingService.findByColorOrSize(color, null, pageable);

        Assertions.assertEquals(clothingSizeColors.getContent().getFirst().getClothing(),
                clothing.getContent().getFirst());
    }
}