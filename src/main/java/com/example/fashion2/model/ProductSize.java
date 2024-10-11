package com.example.fashion2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductSizes")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 10) // Xác định độ dài tối đa của size là 10 ký tự
    private String size; // Thay đổi từ int thành String

    public ProductSize() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; } // Cập nhật kiểu dữ liệu
}
