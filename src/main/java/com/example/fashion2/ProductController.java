package com.example.fashion2;

import com.example.fashion2.model.Product;
import com.example.fashion2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("content", "products/list"); // Reference to actual content fragment
        model.addAttribute("products", products);
        return "layout/adminLayout"; // Template danh sách sản phẩm
    }

    @GetMapping("/create")
    public String createProductForm(Model model) {
        model.addAttribute("content", "products/create"); // Reference to actual content fragment
        model.addAttribute("product", new Product());
        return "layout/adminLayout"; // Template thêm sản phẩm
    }

    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/products"; // Quay lại danh sách sản phẩm
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

