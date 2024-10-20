package com.example.fashion2.service;

import com.example.fashion2.model.Product;
import com.example.fashion2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product product) {
        product = productRepository.save(product);
        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByGender(String gender) {
        return productRepository.findByGender(gender);
    }

    public List<Product> getProductsForMen() {
        return getProductsByGender("man");
    }

    public List<Product> getProductsForWomen() {
        return getProductsByGender("women");
    }

    public List<Product> getProductsForKids() {
        return getProductsByGender("kid");
    }

    public List<Product> getProductsForUnisex() {
        return getProductsByGender("unisex");
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
}
