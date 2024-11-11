package com.example.fashion2.dto;

public class DistrictDTO {
    private Long id; // Đổi từ int sang Long
    private ProvinceDTO province;
    private String name;

    public DistrictDTO(Long id, String name) { // Thay đổi kiểu tham số id thành Long
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProvinceDTO getProvince() {
        return province;
    }

    public void setProvince(ProvinceDTO province) {
        this.province = province;
    }
}
