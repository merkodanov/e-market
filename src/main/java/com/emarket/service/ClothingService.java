package com.emarket.service;

import com.emarket.model.Clothing;
import com.emarket.repository.ClothingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClothingService {
    private final ClothingRepository clothingRepository;

    public Optional<Clothing> findById(long id) {
        return clothingRepository.findById(id);
    }

    public List<Clothing> findByColorAndSize(String color, String size) {
        return null;
    }
}
