package com.emarket.controller;

import com.emarket.dto.ClothingResponseDto;
import com.emarket.mapper.ClothingMapper;
import com.emarket.model.Clothing;
import com.emarket.service.ClothingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/clothing")
@Tag(name = "Clothing controller",
        description = "Контроллер управления одеждой")
@RequiredArgsConstructor
public class ClothingController {
    private final ClothingService clothingService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск одежды по id", responses = {
            @ApiResponse(responseCode = "200", description = "Одежда найдена"),
            @ApiResponse(responseCode = "404", description = "Одежда не найдена")
    })
    public ResponseEntity<ClothingResponseDto> getClothingResponseDto(@PathVariable long id) {
        Optional<Clothing> clothing = clothingService.findById(id);
        if (clothing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ClothingResponseDto clothingResponseDto = ClothingMapper.toResponseDto(clothing.get());

        return ResponseEntity.ok().body(clothingResponseDto);
    }
}
