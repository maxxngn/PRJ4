package com.example.fashion2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories") // Table name for categories
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NULL")
    private java.sql.Timestamp deleted_at;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public java.sql.Timestamp getDeleted_at() { return deleted_at; }
    public void setDeleted_at(java.sql.Timestamp deleted_at) { this.deleted_at = deleted_at; }
}
