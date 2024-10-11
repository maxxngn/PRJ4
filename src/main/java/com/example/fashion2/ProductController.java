package com.example.fashion2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.fashion2.model.Product;
import com.example.fashion2.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/admin/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAllProducts(pageable);

        for (Product product : productPage.getContent()) {
            product.setDescription(product.getDescription().replace("\n", "<br/>"));
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "admin/product/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        return "admin/product/create";
    }

    @PostMapping
    public String createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") int price,
            @RequestParam("qty") int qty,
            @RequestParam("status") boolean status,
            @RequestParam("gender") String gender,
            @RequestParam(value = "sizes", required = false) List<String> sizes,
            @RequestParam(value = "colors", required = false) List<String> colors,
            @RequestParam("images") MultipartFile[] images) {

        // Tạo đối tượng Product
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setQty(qty);
        product.setStatus(status);
        product.setGender(gender); // Set giá trị gender

        // Xử lý hình ảnh và các thuộc tính khác
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                String imageUrl = uploadImage(image);
                if (imageUrl != null) {
                    imageUrls.add(imageUrl);
                }
            }
        }

        // Lưu sản phẩm cùng kích cỡ và màu sắc
        productService.createProduct(product, sizes, colors, imageUrls);

        return "redirect:/admin/products";
    }

    private String uploadImage(MultipartFile image) {
        // Lấy tên gốc của tệp tin
        String originalFilename = image.getOriginalFilename();
    
        // Tạo đường dẫn lưu trữ hình ảnh (sử dụng đường dẫn tuyệt đối)
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/assets/images/";
    
        // Tạo tên mới cho tệp (có thể dùng UUID để đảm bảo tính duy nhất)
        String newFilename = System.currentTimeMillis() + "_" + originalFilename;
    
        try {
            // Tạo thư mục nếu chưa tồn tại
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
    
            // Lưu tệp tin vào thư mục
            File file = new File(directory, newFilename);
            image.transferTo(file);
    
            // Trả về URL đến hình ảnh đã lưu (đường dẫn tương đối)
            return "/assets/images/" + newFilename;
    
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Hoặc có thể ném ra ngoại lệ tùy theo yêu cầu
        }
    }
    
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }
        model.addAttribute("product", product);
        return "admin/product/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            product.setStatus(false); // Cập nhật status thành false thay vì xóa
            productService.updateProduct(id, product);
        }
        return "redirect:/admin/products"; // Chuyển hướng về danh sách sản phẩm
    }

}
