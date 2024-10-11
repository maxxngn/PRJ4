package com.example.fashion2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fashion2.model.ProductColor;

public interface ProductColorRepository extends JpaRepository<ProductColor, Integer> {
}
