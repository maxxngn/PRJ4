package com.example.fashion2.repository;

import com.example.fashion2.model.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        @Query("SELECT p FROM Product p WHERE p.deleted_at IS NULL AND p.gender = :gender ORDER BY p.id DESC")
        Page<Product> findActiveProductsByGenderLIFO(String gender, Pageable pageable);

        @Query("SELECT p FROM Product p WHERE p.deleted_at IS NULL ORDER BY p.id DESC")
        Page<Product> findAllActiveProductsByLIFO(Pageable pageable);

        @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE " +
                        "(:name IS NULL OR p.name LIKE %:name%) AND " +
                        "(:gender IS NULL OR p.gender = :gender) AND " +
                        "(:category IS NULL OR p.category.name = :category) ") // Filter products where deleted_at is null
        Page<Product> findProductsByFilters(String name, String gender, String category, Pageable pageable);

        @Query("SELECT p FROM Product p " +
                        "WHERE p.deleted_at IS NULL " +
                        "AND p.gender = :gender " +
                        "AND (:category IS NULL OR p.category.name = :category) " +
                        "ORDER BY CASE WHEN :sortDirection = 'asc' THEN p.price END ASC, " +
                        "             CASE WHEN :sortDirection = 'desc' THEN p.price END DESC")
        List<Product> findProductsByGenderCategoryAndPrice(String gender, String category, String sortDirection);
}
