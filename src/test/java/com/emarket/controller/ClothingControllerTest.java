package com.emarket.controller;

import com.emarket.dto.ClothingResponseDto;
import com.emarket.mapper.ClothingMapper;
import com.emarket.model.Clothing;
import com.emarket.service.ClothingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClothingController.class)
@AutoConfigureMockMvc(addFilters = false)
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
                .andExpect(status().isNoContent());
    }

    @Test
    void getClothes_By_Size_And_Color_Is_Success() throws Exception {
        List<Clothing> clothingList = List.of(
                new Clothing("title", "description", 1, 1),
                new Clothing("title 2", "description 2", 2, 3));
        String color = "blue";
        String size = "small";
        Mockito.when(clothingService.findByColorAndSize(color, size)).thenReturn(clothingList);

        MvcResult mvcResult = mockMvc.perform(get("/clothes")
                        .param("color", color)
                        .param("size", size))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ClothingResponseDto> responseDtoList = objectMapper.readValue(mvcResult.getResponse()
                .getContentAsString(), new TypeReference<>() {
        });

        Assertions.assertEquals(clothingList.stream().map(ClothingMapper::toResponseDto).toList(),
                responseDtoList);
    }

    @Test
    void getClothes_By_Size_And_Color_Is_Not_Found() throws Exception {
        String color = "blue";
        String size = "small";
        Mockito.when(clothingService.findByColorAndSize(color, size)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/clothes")
                        .param("color", color)
                        .param("size", size))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllClothes_Is_Success() throws Exception {
        List<Clothing> clothes = List.of(new Clothing("title", "des", 1, 1));
        Mockito.when(clothingService.findAllClothes()).thenReturn(clothes);

        MvcResult result = mockMvc.perform(get("/clothes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ClothingResponseDto> clothingResponseDto = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        Assertions.assertEquals(clothes.stream().map(ClothingMapper::toResponseDto).toList(),
                clothingResponseDto);
    }

    @Test
    void getAllClothes_Is_Not_Found() throws Exception {
        Mockito.when(clothingService.findAllClothes()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/clothes"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getClothes_By_Size_Or_Color_Is_Success() throws Exception {
        String color = "blue";
        List<Clothing> clothingList = List.of(new Clothing("t", "d", 1, 2));
        Mockito.when(clothingService.findByColorOrSize(color, null)).thenReturn(
                clothingList);

        MvcResult result = mockMvc.perform(get("/clothes")
                        .param("color", color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ClothingResponseDto> clothingResponseDto = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        Assertions.assertEquals(clothingList.stream().map(ClothingMapper::toResponseDto).toList(),
                clothingResponseDto);
    }

    @Test
    void getClothes_By_Size_Or_Color_Is_Not_Found() throws Exception {
        String color = "color";
        Mockito.when(clothingService.findByColorOrSize(color, null)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/clothes")
                        .param("color", color))
                .andExpect(status().isNoContent());
    }
}