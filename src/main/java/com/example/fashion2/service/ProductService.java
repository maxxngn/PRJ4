package com.example.fashion2.service;

import com.example.fashion2.model.Product;
import com.example.fashion2.model.ProductVariant;
import com.example.fashion2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
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

    public List<Product> getProductsByGender(String gender) {
        return productRepository.findActiveProductsByGenderLIFO(gender);
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
        product.setDeleted_at(Timestamp.from(Instant.now())); // Set current timestamp as deleted_at
        productRepository.save(product); // Save changes to the database
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
