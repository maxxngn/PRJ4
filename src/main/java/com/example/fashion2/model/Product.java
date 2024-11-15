package com.example.fashion2.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private int price;

    @Column(length = 10)
    private String gender;

    @Column(length = 50)
    private String category;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls; // List of image URLs

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Where(clause = "deleted_at IS NULL")
    private List<ProductVariant> variants;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NULL")
    private Timestamp deleted_at;  // Use java.sql.Timestamp


    public Product() {} 

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public List<ProductVariant> getVariants() { return variants; }
    public void setVariants(List<ProductVariant> variants) { this.variants = variants; }

    public Timestamp getDeletedAt() { return deleted_at; }
    public void setDeletedAt(Timestamp deleted_at) { this.deleted_at = deleted_at; }
}
