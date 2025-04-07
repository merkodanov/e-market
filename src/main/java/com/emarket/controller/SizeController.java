package com.emarket.controller;

import com.emarket.model.Size;
import com.emarket.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
public class SizeController {
    private final SizeRepository sizeRepository;

    @GetMapping("/sizes")
    public ResponseEntity<List<Size>> getAllSize() {
        List<Size> sizes = StreamSupport.stream(sizeRepository.findAll().spliterator(), false).toList();
        return ResponseEntity.ok(sizes);
    }
}
