package com.example.fashion2.repository;

import com.example.fashion2.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
    void deleteByProductId(int productId);
}
