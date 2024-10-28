package com.example.fashion2.service;

import com.example.fashion2.model.Voucher;
import com.example.fashion2.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return voucherRepository.findAll();
    }

    public Optional<Voucher> getVoucherById(int id) {
        return voucherRepository.findById(id);
    }

    public Voucher createVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
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
        voucher.setStatus(false); // Set status to false instead of deleting
        voucherRepository.save(voucher);
    }
}
