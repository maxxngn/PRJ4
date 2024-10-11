package com.example.fashion2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fashion2.model.ProductSize;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
}
