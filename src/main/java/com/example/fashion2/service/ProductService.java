package com.example.fashion2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fashion2.model.Product;
import com.example.fashion2.model.ProductColor;
import com.example.fashion2.model.ProductImage;
import com.example.fashion2.model.ProductSize;
import com.example.fashion2.repository.ProductColorRepository;
import com.example.fashion2.repository.ProductImageRepository;
import com.example.fashion2.repository.ProductRepository;
import com.example.fashion2.repository.ProductSizeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductColorRepository productColorRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProductsByGender(String gender) {
        return productRepository.findByGender(gender);
    }

    @Transactional
    public Product createProduct(Product product, List<String> sizes, List<String> colors, List<String> images) {
        Product savedProduct = productRepository.save(product);

        // Lưu kích cỡ
        if (sizes != null) {
            for (String size : sizes) {
                ProductSize productSize = new ProductSize();
                productSize.setProduct(savedProduct);
                productSize.setSize(size); // Set size as String
                productSizeRepository.save(productSize);
            }
        }

        // Lưu màu sắc
        if (colors != null) {
            for (String color : colors) {
                ProductColor productColor = new ProductColor();
                productColor.setProduct(savedProduct);
                productColor.setColor(color);
                productColorRepository.save(productColor);
            }
        }

        // Lưu hình ảnh
        if (images != null) {
            for (String imageUrl : images) {
                ProductImage productImage = new ProductImage();
                productImage.setProduct(savedProduct);
                productImage.setImageUrl(imageUrl);
                productImageRepository.save(productImage);
            }
        }

        return savedProduct;
    }

    public Product updateProduct(Integer id, Product productDetails) {
        Product product = getProductById(id);
        if (product != null) {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setQty(productDetails.getQty());
            product.setStatus(productDetails.isStatus());
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
