package com.example.fashion2.service;

import com.example.fashion2.model.Product;
import com.example.fashion2.model.ProductVariant;
import com.example.fashion2.repository.ProductRepository;
import com.example.fashion2.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductVariant createProductVariant(ProductVariant productVariant, int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        productVariant.setProduct(product);
        return productVariantRepository.save(productVariant);
    }
}
