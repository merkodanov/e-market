package com.emarket.controller;

import com.emarket.dto.ClothingResponseDto;
import com.emarket.mapper.ClothingMapper;
import com.emarket.model.Clothing;
import com.emarket.service.ClothingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        List<String> color = List.of("blue", "red");
        List<String> size = List.of("small", "big");
        int offset = 0;
        int limit = 1;
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Clothing> page = new PageImpl<>(clothingList);
        Mockito.when(clothingService.findByColorAndSize(color, size, pageable)).thenReturn(page);

        MvcResult mvcResult = mockMvc.perform(get("/clothes")
                        .param("color", String.join(",", color))
                        .param("size", String.join(",", size))
                        .param("offset", String.valueOf(offset))
                        .param("limit", String.valueOf(limit)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
        JsonNode contentNode = rootNode.get("content");
        List<ClothingResponseDto> clothingResponse = objectMapper.readValue(contentNode.toString(), new TypeReference<>() {
        });

        Assertions.assertEquals(clothingList.stream().map(ClothingMapper::toResponseDto).toList(), clothingResponse);
    }

    @Test
    void getClothes_By_Size_And_Color_Is_Not_Found() throws Exception {
        List<String> color = List.of("blue", "red");
        List<String> size = List.of("small", "big");
        int offset = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(offset, limit);
        Mockito.when(clothingService.findByColorAndSize(color, size, pageable)).thenReturn(Page.empty());

        mockMvc.perform(get("/clothes")
                        .param("color", String.join(",", color))
                        .param("size", String.join(",", size))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllClothes_Is_Success() throws Exception {
        Page<Clothing> clothes = new PageImpl<>(List.of(new Clothing("title", "des", 1, 1)));
        int offset = 0;
        int limit = 1;
        Pageable pageable = PageRequest.of(offset, limit);
        Mockito.when(clothingService.findAllClothes(pageable)).thenReturn(clothes);

        MvcResult result = mockMvc.perform(get("/clothes")
                        .param("offset", String.valueOf(offset))
                        .param("limit", String.valueOf(limit)))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode contentNode = rootNode.get("content");
        List<ClothingResponseDto> clothingResponseDto = objectMapper.readValue(contentNode.toString(), new TypeReference<>() {
        });

        Assertions.assertEquals(clothes.map(ClothingMapper::toResponseDto).toList(),
                clothingResponseDto);
    }

    @Test
    void getAllClothes_Is_Not_Found() throws Exception {
        int offset = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(offset, limit);
        Mockito.when(clothingService.findAllClothes(pageable)).thenReturn(Page.empty());

        mockMvc.perform(get("/clothes"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getClothes_By_Size_Or_Color_Is_Success() throws Exception {
        List<String> color = List.of("blue", "red");
        Page<Clothing> clothingList = new PageImpl<>(List.of(new Clothing("t", "d", 1, 2)));
        int offset = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(offset, limit);
        Mockito.when(clothingService.findByColorOrSize(color, null, pageable)).thenReturn(
                clothingList);

        MvcResult result = mockMvc.perform(get("/clothes")
                        .param("color", String.join(",", color))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode contentNode = rootNode.get("content");
        List<ClothingResponseDto> clothingResponseDto = objectMapper.readValue(contentNode.toString(), new TypeReference<>() {
        });
        Assertions.assertEquals(clothingList.map(ClothingMapper::toResponseDto).toList(),
                clothingResponseDto);
    }

    @Test
    void getClothes_By_Size_Or_Color_Is_Not_Found() throws Exception {
        List<String> color = List.of("color");
        int offset = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(offset, limit);
        Mockito.when(clothingService.findByColorOrSize(color, null, pageable)).thenReturn(Page.empty());

        mockMvc.perform(get("/clothes")
                        .param("color", String.join(",", color)))
                .andExpect(status().isNoContent());
    }

    @Test
    void getClothes_Offset_Below_Zero_Is_Bad_Request() {
        Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(get("/clothes")
                .param("offset", "-1")));
    }

    @Test
    void getClothes_Limit_Below_1_Is_Bad_Request() {
        Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(get("/clothes")
                .param("limit", "0")));
    }

    @Test
    void getClothes_Offset_Above_100_Is_Bad_Request() {
        Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(get("/clothes")
                .param("offset", "101")));
    }
}