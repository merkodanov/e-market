package com.emarket.controller;

import com.emarket.dto.ClothingResponseDto;
import com.emarket.mapper.ClothingMapper;
import com.emarket.service.ClothingService;
import com.emarket.model.Clothing;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clothing")
public class ClothingController {
    private final ClothingService clothingService;

    @GetMapping("/{id}")
    public ResponseEntity<ClothingResponseDto> getClothingResponseDto(@PathVariable long id) {
        Optional<Clothing> clothing = clothingService.findById(id);
        if (clothing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClothingResponseDto clothingResponseDto = ClothingMapper.toResponseDto(clothing.get());

        return ResponseEntity.ok().body(clothingResponseDto);
    }
}
