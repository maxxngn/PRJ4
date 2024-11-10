package com.example.fashion2.controller;

import com.example.fashion2.model.ProductVariant;
import com.example.fashion2.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    @PostMapping("/create/{productId}")
    public ResponseEntity<ProductVariant> createProductVariant(@RequestBody ProductVariant productVariant,
            @PathVariable int productId) {
        System.out.println("xxx");
        ProductVariant createdProductVariant = productVariantService.createProductVariant(productVariant, productId);
        return ResponseEntity.ok(createdProductVariant);
    }
}
