package com.example.fashion2.controller;

import com.example.fashion2.model.Order;
import com.example.fashion2.service.DashboardService;
import com.example.fashion2.service.DistrictService;
import com.example.fashion2.service.OrderService;
import com.example.fashion2.service.ProvinceService;
import com.example.fashion2.service.WardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

     @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders);
    }
}
