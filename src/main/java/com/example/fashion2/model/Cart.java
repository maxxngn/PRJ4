//package com.example.fashion2.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Cart {
//    private List<Product> products = new ArrayList<>();
//
//    public void addProduct(Product product) {
//        products.add(product);
//    }
//
//    public void removeProduct(Product product) {
//        products.remove(product);
//    }
//
//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public double getTotalPrice() {
//        return products.stream().mapToDouble(Product::getPrice).sum();
//    }
//}
