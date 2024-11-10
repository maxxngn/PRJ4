package com.example.fashion2.service;

import com.example.fashion2.model.Address;
import com.example.fashion2.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesByUserId(int userId) {
        return addressRepository.findByUserId(userId);
    }

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address deleteAddress(int id) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address != null) {
            addressRepository.delete(address);
            return address;
        }
        return null;
    }
}
