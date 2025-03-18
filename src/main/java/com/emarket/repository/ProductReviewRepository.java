package com.emarket.repository;

import com.emarket.model.ProductReview;
import org.springframework.data.repository.CrudRepository;

public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {
}
