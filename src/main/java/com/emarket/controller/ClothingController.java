package com.emarket.controller;

import com.emarket.dto.ClothingResponseDto;
import com.emarket.mapper.ClothingMapper;
import com.emarket.model.Clothing;
import com.emarket.service.ClothingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Clothing controller",
        description = "Контроллер управления одеждой")
@RequiredArgsConstructor
public class ClothingController {
    private final ClothingService clothingService;

    @GetMapping(value = "/clothing/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск одежды по id", responses = {
            @ApiResponse(responseCode = "200", description = "Одежда найдена"),
            @ApiResponse(responseCode = "204", description = "Одежда не найдена")
    })
    public ResponseEntity<ClothingResponseDto> getClothingResponseDto(@PathVariable long id) {
        Optional<Clothing> clothing = clothingService.findById(id);
        if (clothing.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        ClothingResponseDto clothingResponseDto = ClothingMapper.toResponseDto(clothing.get());

        return ResponseEntity.ok().body(clothingResponseDto);
    }

    @GetMapping(value = "/clothes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Поиск одежды по цвету и размеру вместе с пагинацией", responses = {
            @ApiResponse(responseCode = "200", description = "Одежда найдена"),
            @ApiResponse(responseCode = "204", description = "Одежда не найдена")
    })
    public ResponseEntity<Page<ClothingResponseDto>> getClothesByColorAndSize(@RequestParam(required = false)
                                                                              List<String> color,
                                                                              @RequestParam(required = false)
                                                                              List<String> size,
                                                                              @RequestParam(value = "offset",
                                                                                      defaultValue = "0")
                                                                              int offset,
                                                                              @RequestParam(value = "limit",
                                                                                      defaultValue = "10")
                                                                              int limit) {
        Pageable pageRequest = PageRequest.of(offset, limit);

        if (color == null && size == null) {
            Page<Clothing> clothes = clothingService.findAllClothes(pageRequest);
            return clothes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(clothes.map(ClothingMapper::toResponseDto));
        }

        if (color == null || size == null) {
            Page<Clothing> clothes = clothingService.findByColorOrSize(color, size, pageRequest);
            return clothes.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(clothes.map(ClothingMapper::toResponseDto));
        }

        Page<Clothing> clothes = clothingService.findByColorAndSize(color, size, pageRequest);
        return clothes.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(clothes.map(ClothingMapper::toResponseDto));
    }

}
