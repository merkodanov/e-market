package com.emarket.controller;

import com.emarket.dto.ClothingResponseDto;
import com.emarket.mapper.ClothingMapper;
import com.emarket.model.Clothing;
import com.emarket.service.ClothingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClothingController.class)
class ClothingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClothingService clothingService;

    @Test
    void getResponseDto_Is_Success() throws Exception {
        Clothing clothing = new Clothing("title", "description", 1, 1);
        long id = 1;
        clothing.setId(id);
        ClothingResponseDto clothingResponseDto = ClothingMapper.toResponseDto(clothing);
        Mockito.when(clothingService.findById(id)).thenReturn(Optional.of(clothing));

        MvcResult result = mockMvc.perform(get("/clothing/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ClothingResponseDto actualClothingResponseDto = objectMapper
                .readValue(responseBody, ClothingResponseDto.class);

        Assertions.assertEquals(clothingResponseDto, actualClothingResponseDto);
    }

    @Test
    void getResponseDto_Is_Not_Found() throws Exception {
        long id = 1;
        Mockito.when(clothingService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clothing/{id}", id))
                .andExpect(status().isNotFound());
    }
}