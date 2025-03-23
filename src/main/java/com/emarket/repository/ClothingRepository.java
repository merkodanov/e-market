package com.emarket.repository;

import com.emarket.model.Clothing;
import org.springframework.data.repository.CrudRepository;

public interface ClothingRepository extends CrudRepository<Clothing, Long> {
}
