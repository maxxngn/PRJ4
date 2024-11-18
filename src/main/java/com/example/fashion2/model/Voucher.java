package com.example.fashion2.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "Vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;
    private int discount;
    private int qty;
    private String description;

    // New field
    private boolean status; // true for active, false for inactive

    public Voucher() {
        this.status = true; // Set default status to true (active)
    }

    // add deleted at timestamp column can null
    @Column(columnDefinition = "TIMESTAMP DEFAULT NULL")
    private Timestamp deleted_at;  // Use java.sql.Timestamp


    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getDiscount() { return discount; }
    public void setDiscount(int discount) { this.discount = discount; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public Timestamp getDeleted_at() { return deleted_at; }
    public void setDeleted_at(Timestamp deleted_at) { this.deleted_at = deleted_at; }
}
