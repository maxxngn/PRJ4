package com.example.fashion2.repository;

import com.example.fashion2.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    @Query("SELECT pv FROM ProductVariant pv JOIN FETCH pv.product WHERE pv.id = :id")
    ProductVariant findProductVariantWithProduct(int id);
}
