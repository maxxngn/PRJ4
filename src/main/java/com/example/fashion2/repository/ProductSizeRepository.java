package com.example.fashion2.repository;

import com.example.fashion2.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {
    void deleteByProductId(int productId);
}
