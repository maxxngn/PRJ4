package com.example.fashion2.dto;

import java.util.List;

import com.example.fashion2.dto.OrderResponseDTO.OrderDetailDTO;

public class OrderDTO {
    private int id;
    private VoucherDTO voucher;
    private int status;
    private String payment;
    private String phone;
    private int price;
    private AddressDTO address;
    private List<OrderDetailDTO> orderDetails;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public VoucherDTO getVoucher() {
        return voucher;
    }
    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getPayment() {
        return payment;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public AddressDTO getAddress() {
        return address;
    }
    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    // Getters và Setters
}
