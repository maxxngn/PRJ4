package com.example.fashion2.controller;

import com.example.fashion2.model.Address;
import com.example.fashion2.repository.UserRepository;
import com.example.fashion2.repository.WardRepository;
import com.example.fashion2.model.User;
import com.example.fashion2.model.Ward;
import com.example.fashion2.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WardRepository wardRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUserId(@PathVariable int userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        if (addresses.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về mã 204 nếu không có địa chỉ nào
        }
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/create")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address savedAddress = addressService.createAddress(address);
        return ResponseEntity.ok(savedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable int id) {
        System.out.println("xxx123");
        Address deletedAddress = addressService.deleteAddress(id);
        if (deletedAddress != null) {
            return ResponseEntity.ok(deletedAddress);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
