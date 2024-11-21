package com.example.fashion2.controller;

import com.example.fashion2.dto.DistrictDTO;
import com.example.fashion2.dto.OrderResponseDTO;
import com.example.fashion2.dto.ProvinceDTO;
import com.example.fashion2.dto.WardDTO;
import com.example.fashion2.model.Order;
import com.example.fashion2.service.DistrictService;
import com.example.fashion2.service.OrderService;
import com.example.fashion2.service.ProvinceService;
import com.example.fashion2.service.WardService;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;
    @Autowired
    private WardService wardService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(createdOrder);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending confirmation email: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@RequestParam(required = false) Integer status) {
        List<OrderResponseDTO> orders = orderService.getAllOrders(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        System.out.println("xxx");
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces() {
        List<ProvinceDTO> provinces = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinces);
    }

    @GetMapping("/districts/{provinceId}")
    public ResponseEntity<List<DistrictDTO>> getDistrictsByProvince(@PathVariable int provinceId) {
        List<DistrictDTO> districts = districtService.getDistrictsByProvince(provinceId);
        return ResponseEntity.ok(districts);
    }

    @GetMapping("/wards/{districtId}")
    public ResponseEntity<List<WardDTO>> getWardsByDistrict(@PathVariable int districtId) {
        List<WardDTO> wards = wardService.getWardsByDistrict(districtId);
        return ResponseEntity.ok(wards);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable int id) {
        Order updatedOrder = orderService.updateOrderStatus(id);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable int userId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable int id) {
        Order updatedOrder = orderService.cancelOrder(id);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/accept-cancel/{id}")
    public ResponseEntity<Order> acceptCancelOrder(@PathVariable int id) {
        Order updatedOrder = orderService.acceptCancelOrder(id);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
