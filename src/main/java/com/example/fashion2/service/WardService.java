package com.example.fashion2.service;

import com.example.fashion2.dto.WardDTO;
import com.example.fashion2.model.Ward;
import com.example.fashion2.repository.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WardService {
    @Autowired
    private WardRepository wardRepository;

    public List<WardDTO> getWardsByDistrict(int districtId) {
        List<Ward> wards = wardRepository.findByDistrictId(districtId);
        return wards.stream()
                .map(ward -> new WardDTO(ward.getId(), ward.getName()))
                .collect(Collectors.toList());
    }
}
