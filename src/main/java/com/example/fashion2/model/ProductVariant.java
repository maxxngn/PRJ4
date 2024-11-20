package com.example.fashion2.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductVariants")
@JsonIgnoreProperties({"product"}) 
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20)
    private String size;

    @Column(length = 20)
    private String color;

    private int qty;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NULL")
    private Timestamp deleted_at;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    // @JsonIgnore
    private Product product;    

    public ProductVariant() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getProductId() {
        return product != null ? product.getId() : 0; // Return product ID if product exists, else 0
    }

    public String getProductName() {
        return product != null ? product.getName() : ""; // Return product name if product exists, else empty string
    }

    public Timestamp getDeleted_at() { return deleted_at; }
    public void setDeleted_at(Timestamp deleted_at) { this.deleted_at = deleted_at; }
}
