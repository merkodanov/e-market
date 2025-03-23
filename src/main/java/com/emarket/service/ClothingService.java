package com.emarket.service;

import com.emarket.model.Clothing;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClothingService {
    public Optional<Clothing> findById(long id) {
        return Optional.empty();
    }
}
