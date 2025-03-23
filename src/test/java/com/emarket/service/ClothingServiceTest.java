package com.emarket.service;

import com.emarket.model.Clothing;
import com.emarket.repository.ClothingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClothingServiceTest {
    @Mock
    private ClothingRepository clothingRepository;

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
}