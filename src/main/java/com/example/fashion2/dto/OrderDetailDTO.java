package com.example.fashion2.dto;

public class OrderDetailDTO {
    private int id;
    private ProductVariantDTO productVariant;
    private int quantity;
    private int price;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ProductVariantDTO getProductVariant() {
        return productVariant;
    }
    public void setProductVariant(ProductVariantDTO productVariant) {
        this.productVariant = productVariant;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    // Getters và Setters
}