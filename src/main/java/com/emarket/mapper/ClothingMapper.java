package com.emarket.mapper;

import com.emarket.dto.ClothingResponseDto;
import com.emarket.model.Clothing;

public class ClothingMapper {
    public static ClothingResponseDto toResponseDto(Clothing clothing) {
        return new ClothingResponseDto(clothing.getId(), clothing.getTitle(), clothing.getDescription(),
                clothing.getRating(), clothing.getPrice());
    }
}
