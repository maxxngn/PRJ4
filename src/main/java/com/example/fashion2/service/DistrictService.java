package com.example.fashion2.service;

import com.example.fashion2.dto.DistrictDTO;
import com.example.fashion2.model.District;
import com.example.fashion2.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    public List<DistrictDTO> getDistrictsByProvince(int provinceId) {
        List<District> districts = districtRepository.findByProvinceId(provinceId);
        return districts.stream()
                .map(district -> new DistrictDTO(district.getId(), district.getName()))
                .collect(Collectors.toList());
    }
}
