package com.example.fashion2.repository;

import com.example.fashion2.model.Voucher;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    // You can define custom query methods here if needed
    Optional<Voucher> findByCode(String code); // New method to find voucher by code

    @Query("SELECT v FROM Voucher v WHERE v.deleted_at IS NULL")
    List<Voucher> findAllActiveVouchers();
}
