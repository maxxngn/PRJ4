package com.example.fashion2.repository;

import com.example.fashion2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.deleted_at IS NULL AND p.gender = :gender ORDER BY p.id DESC")
    List<Product> findActiveProductsByGenderLIFO(String gender);

    @Query("SELECT p FROM Product p WHERE p.deleted_at IS NULL ORDER BY p.id DESC")
    List<Product> findAllActiveProductsByLIFO();
}
