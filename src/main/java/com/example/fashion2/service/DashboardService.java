package com.example.fashion2.service;

import com.example.fashion2.model.OrderDetail;
import com.example.fashion2.model.Product;
import com.example.fashion2.repository.OrderDetailRepository;
import com.example.fashion2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public Map<String, Object> getDashboardData() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        List<Product> allProducts = productRepository.findAll();

        int totalSoldProducts = 0;
        int totalRevenue = 0;
        Map<Integer, Integer> productSoldCount = new HashMap<>();

        // Tính tổng sản phẩm đã bán và tổng doanh thu
        for (OrderDetail orderDetail : orderDetails) {
            int quantity = orderDetail.getQuantity();
            int price = orderDetail.getPrice();

            totalSoldProducts += quantity;
            totalRevenue += quantity * price;

            // Cộng dồn số lượng bán của sản phẩm
            int productId = orderDetail.getProductVariant().getProduct().getId();
            productSoldCount.put(productId, productSoldCount.getOrDefault(productId, 0) + quantity);
        }

        Set<Integer> uniqueOrders = new HashSet<>();
        for (OrderDetail orderDetail : orderDetails) {
            uniqueOrders.add(orderDetail.getOrder().getId());
        }
        int totalOrders = uniqueOrders.size();  
        int totalProducts = allProducts.size();

        // Tính sản phẩm bán ít nhất và nhiều nhất
        int minSoldProductCount = Integer.MAX_VALUE;
        int maxSoldProductCount = Integer.MIN_VALUE;
        String minSoldProductName = "No sales";
        String maxSoldProductName = "No sales";

        for (Map.Entry<Integer, Integer> entry : productSoldCount.entrySet()) {
            int soldCount = entry.getValue();
            int productId = entry.getKey();

            // Tìm sản phẩm bán ít nhất
            if (soldCount < minSoldProductCount) {
                minSoldProductCount = soldCount;
                minSoldProductName = getProductNameById(productId, allProducts);
            }

            // Tìm sản phẩm bán nhiều nhất
            if (soldCount > maxSoldProductCount) {
                maxSoldProductCount = soldCount;
                maxSoldProductName = getProductNameById(productId, allProducts);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalSoldProducts", totalSoldProducts);
        result.put("totalRevenue", totalRevenue);
        result.put("totalOrders", totalOrders); 
        result.put("totalProducts", totalProducts);
        result.put("minSoldProduct", minSoldProductName);
        result.put("maxSoldProduct", maxSoldProductName);

        return result;
    }

    // Hàm giúp lấy tên sản phẩm theo ID
    private String getProductNameById(int productId, List<Product> allProducts) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product.getName();
            }
        }
        return "Unknown Product";
    }
}
