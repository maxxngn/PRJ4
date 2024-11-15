package com.example.fashion2.service;

import com.example.fashion2.model.Product;
import com.example.fashion2.model.ProductVariant;
import com.example.fashion2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product product) {
        // Set the product reference in each variant
        List<ProductVariant> variants = product.getVariants();
        if (variants != null) {
            for (ProductVariant variant : variants) {
                variant.setProduct(product); // Associate each variant with the product
            }
        }
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllActiveProductsByLIFO();
    }

    public Page<Product> searchProducts(String name, String gender, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findProductsByFilters(
            name != null && !name.isEmpty() ? name : null,
            gender != null && !gender.isEmpty() ? gender : null,
            category != null && !category.isEmpty() ? category : null,
            pageable
        );
    }

    public List<Product> getProductsByGenderAndCategoryAndSort(String gender, String category, String sortDirection) {
        return productRepository.findProductsByGenderCategoryAndPrice(gender, category, sortDirection);
    }
    
    public List<Product> getProductsForMen(String category, String sortDirection) {
        return getProductsByGenderAndCategoryAndSort("man", category, sortDirection);
    }
    
    public List<Product> getProductsForWomen(String category, String sortDirection) {
        return getProductsByGenderAndCategoryAndSort("women", category, sortDirection);
    }
    
    public List<Product> getProductsForKids(String category, String sortDirection) {
        return getProductsByGenderAndCategoryAndSort("kid", category, sortDirection);
    }
    
    public List<Product> getProductsForUnisex(String category, String sortDirection) {
        return getProductsByGenderAndCategoryAndSort("unisex", category, sortDirection);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getDeletedAt() == null) {
            product.setDeletedAt(Timestamp.from(Instant.now())); // Gán thời gian hiện tại
            productRepository.save(product);
            return true; // Đã xóa
        } else {
            product.setDeletedAt(null); // Hủy xóa
            productRepository.save(product);
            return false; // Đã hủy xóa
        }
    }

    @Transactional
    public Product updateProduct(int id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setGender(updatedProduct.getGender());
        existingProduct.setStatus(updatedProduct.isStatus());
        existingProduct.setImageUrls(updatedProduct.getImageUrls());

        return productRepository.save(existingProduct);
    }
}
