package com.example.fashion2.dto;

import java.util.List;

public class CreateProductRequest {
    private String name;
    private String description;
    private int price;
    private int qty;
    private String gender;
    private boolean status;

    private List<String> colors;
    private List<String> sizes;
    private List<String> images;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public List<String> getColors() { return colors; }
    public void setColors(List<String> colors) { this.colors = colors; }

    public List<String> getSizes() { return sizes; }
    public void setSizes(List<String> sizes) { this.sizes = sizes; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
}
