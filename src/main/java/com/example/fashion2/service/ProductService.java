//package com.example.fashion2.service;
//
//import com.example.fashion2.model.Product;
//import com.example.fashion2.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ProductService {
//    @Autowired
//    private ProductRepository productRepository;
//
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
//    public Product saveProduct(Product product) {
//        return productRepository.save(product);
//    }
//    public Product findById(Long id) {
//        return productRepository.findById(id).orElse(null); // Возвращает null, если продукт не найден
//    }
//}
