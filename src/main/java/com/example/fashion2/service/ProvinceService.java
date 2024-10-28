package com.example.fashion2.service;

import com.example.fashion2.dto.ProvinceDTO;
import com.example.fashion2.model.Province;
import com.example.fashion2.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    public List<ProvinceDTO> getAllProvinces() {
        List<Province> provinces = provinceRepository.findAll();
        return provinces.stream()
                .map(province -> new ProvinceDTO(province.getId(), province.getName())) // Đảm bảo province.getId() trả về Long
                .collect(Collectors.toList());
    }
}
