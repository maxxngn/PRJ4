package com.example.fashion2.service;

import com.example.fashion2.dto.ProductVariantDTO;
import com.example.fashion2.dto.ProductVariantResponseDTO;
import com.example.fashion2.model.Product;
import com.example.fashion2.model.ProductVariant;
import com.example.fashion2.repository.ProductRepository;
import com.example.fashion2.repository.ProductVariantRepository;

import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductVariant createProductVariant(ProductVariantDTO productVariantDTO) {
        // Find the product by ID
        Product product = productRepository.findById(productVariantDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create a new ProductVariant and set the details
        ProductVariant productVariant = new ProductVariant();
        productVariant.setSize(productVariantDTO.getSize());
        productVariant.setColor(productVariantDTO.getColor());
        productVariant.setQty(productVariantDTO.getQty());
        productVariant.setProduct(product);

        // Save the ProductVariant to the database
        productVariantRepository.save(productVariant);
        // return message success, dont return the object;
        return productVariant;
    }

    public List<ProductVariantResponseDTO> getAllProductVariants() {
        List<ProductVariant> productVariants = productVariantRepository.findAll();
        return productVariants.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public boolean updateProductVariant(int id, ProductVariantDTO productVariantDTO) {
        // Find the product variant by ID
        ProductVariant productVariant = productVariantRepository.findById(id).orElse(null);
        if (productVariant != null) {
            // Update the product variant details
            productVariant.setSize(productVariantDTO.getSize());
            productVariant.setColor(productVariantDTO.getColor());
            productVariant.setQty(productVariantDTO.getQty());

            // Optionally, if the product ID changes, you can update the product as well
            if (productVariantDTO.getProductId() != 0) {
                Product product = productRepository.findById(productVariantDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                productVariant.setProduct(product);
            }

            // Save the updated ProductVariant to the database
            productVariantRepository.save(productVariant);
            return true;
        }
        return false; // Product variant not found
    }

    // Convert ProductVariant entity to ProductVariantResponseDTO
    private ProductVariantResponseDTO convertToDto(ProductVariant productVariant) {
        ProductVariantResponseDTO dto = new ProductVariantResponseDTO();
        dto.setId(productVariant.getId());
        dto.setSize(productVariant.getSize());
        dto.setColor(productVariant.getColor());
        dto.setQty(productVariant.getQty());

        // Map Product details inside ProductVariant
        ProductVariantResponseDTO.ProductDTO productDTO = new ProductVariantResponseDTO.ProductDTO();
        productDTO.setId(productVariant.getProduct().getId());
        productDTO.setName(productVariant.getProduct().getName());
        productDTO.setDescription(productVariant.getProduct().getDescription());
        productDTO.setPrice(productVariant.getProduct().getPrice());

        dto.setProduct(productDTO);
        return dto;
    }

    @Transactional
    public boolean deleteProductVariant(int id) {
        ProductVariant productVariant = productVariantRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Product variant not found"));

        if(productVariant.getDeleted_at() == null) {
            productVariant.setDeleted_at(Timestamp.from(Instant.now()));
            productVariantRepository.save(productVariant);
            return true;
        } else {
            productVariant.setDeleted_at(null);
            productVariantRepository.save(productVariant);
            return false;
        }
    }
}
