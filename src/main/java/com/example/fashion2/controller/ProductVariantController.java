package com.example.fashion2.controller;

import com.example.fashion2.dto.ProductVariantDTO;
import com.example.fashion2.dto.ProductVariantResponseDTO;
import com.example.fashion2.service.ProductVariantService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<String> createProductVariant(@RequestBody ProductVariantDTO productVariantDTO) {
        productVariantService.createProductVariant(productVariantDTO);
        return ResponseEntity.status(201).body("Product Variant created successfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductVariantResponseDTO>> getAllProductVariants() {
        List<ProductVariantResponseDTO> productVariants = productVariantService.getAllProductVariants();
        return ResponseEntity.ok(productVariants);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductVariant(@PathVariable int id, @RequestBody ProductVariantDTO productVariantDTO) {
        boolean isUpdated = productVariantService.updateProductVariant(id, productVariantDTO);
        if (isUpdated) {
            return ResponseEntity.ok("Product Variant updated successfully");
        } else {
            return ResponseEntity.status(404).body("Product Variant not found");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProductVariant(@PathVariable int id) {
        boolean isDeleted = productVariantService.deleteProductVariant(id);
        if (isDeleted) {
            return ResponseEntity.ok("Product Variant deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Product Variant not found");
        }
    }
}
