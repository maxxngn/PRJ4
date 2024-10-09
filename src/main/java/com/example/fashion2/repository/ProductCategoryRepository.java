package com.example.fashion2.repository;

import com.example.fashion2.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    void deleteByProductId(Integer productId);
}
