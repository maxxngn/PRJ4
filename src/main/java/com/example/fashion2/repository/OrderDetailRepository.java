package com.example.fashion2.repository;

import com.example.fashion2.model.OrderDetail;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @EntityGraph(attributePaths = { "productVariant", "productVariant.product", "productVariant.size",
            "productVariant.color" })
    List<OrderDetail> findByOrderId(int orderId);
}
