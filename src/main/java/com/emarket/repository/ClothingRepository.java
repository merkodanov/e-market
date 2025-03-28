package com.emarket.repository;

import com.emarket.model.Clothing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ClothingRepository extends CrudRepository<Clothing, Long> {
    Page<Clothing> findAll(Pageable pageable);
}
