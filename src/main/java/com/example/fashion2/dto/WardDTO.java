package com.example.fashion2.dto;

public class WardDTO {
    private Long id; // Đổi từ int sang Long
    private String name;

    public WardDTO(Long id, String name) { // Thay đổi kiểu tham số id thành Long
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
