package com.example.fashion2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fashion2.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByGender(String gender);
}
