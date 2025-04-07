package com.emarket.controller;

import com.emarket.model.Color;
import com.emarket.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
public class ColorController {
    private final ColorRepository colorRepository;

    @GetMapping("/colors")
    public ResponseEntity<List<Color>> getAllColors() {
        List<Color> colors = StreamSupport.stream(colorRepository.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(colors);
    }
}
