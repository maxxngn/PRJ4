package com.example.fashion2;

import com.example.fashion2.model.Product;
import com.example.fashion2.model.ProductImage;
import com.example.fashion2.repository.ProductImageRepository;
import com.example.fashion2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("content", "products/list");
        model.addAttribute("products", products);
        return "layout/adminLayout";
    }

    @GetMapping("/create")
    public String createProductForm(Model model) {
        model.addAttribute("content", "products/create"); // Reference to actual content fragment
        model.addAttribute("product", new Product());
        return "layout/adminLayout"; // Template thêm sản phẩm
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Integer id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("content", "products/edit"); // Reference to actual content fragment
        model.addAttribute("product", product);
        return "layout/adminLayout"; // Template chỉnh sửa sản phẩm
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Integer id, @ModelAttribute Product product) {
        product.setId(id);
        productRepository.save(product);
        return "redirect:/admin/products"; // Quay lại danh sách sản phẩm
    }

    @GetMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products"; // Quay lại danh sách sản phẩm
    }
}
