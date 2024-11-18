package com.example.fashion2.repository;

import com.example.fashion2.model.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.deleted_at IS NULL ORDER BY c.id DESC")
    List<Category> findAllActiveCategories();
}
