package com.example.fashion2.repository;

import com.example.fashion2.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    // You can define custom query methods here if needed
}
