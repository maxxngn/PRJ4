package com.example.fashion2.repository;

import com.example.fashion2.model.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, Integer> {
    void deleteByProductId(int productId);
}
