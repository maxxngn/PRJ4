package com.example.fashion2.service;

import com.example.fashion2.model.Voucher;
import com.example.fashion2.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    
    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAllActiveVouchers();
    }

    public Optional<Voucher> getVoucherById(int id) {
        return voucherRepository.findById(id);
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Optional<Voucher> getVoucherByCode(String code) {
        Optional<Voucher> voucher = voucherRepository.findByCode(code);
        // Check if voucher is present and if its quantity is greater than 0
        if (voucher.isPresent() && voucher.get().getQty() == 0) {
            return Optional.empty();  // Return empty Optional if quantity is 0
        }
        return voucher;
    }

    public Voucher updateVoucher(int id, Voucher voucherDetails) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        voucher.setCode(voucherDetails.getCode());
        voucher.setDiscount(voucherDetails.getDiscount());
        voucher.setQty(voucherDetails.getQty());
        voucher.setDescription(voucherDetails.getDescription());
        voucher.setStatus(voucherDetails.isStatus()); // Update status if provided
        return voucherRepository.save(voucher);
    }

    public void deleteVoucher(int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        voucher.setDeleted_at(Timestamp.from(Instant.now()));
        voucherRepository.save(voucher);
    }
}
