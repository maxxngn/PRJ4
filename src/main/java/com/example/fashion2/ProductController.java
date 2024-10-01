//package com.example.fashion2;
//
//import com.example.fashion2.model.Product;
//import com.example.fashion2.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/api/products")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    // API для получения всех продуктов
//    @GetMapping
//    @ResponseBody // Указываем, что возвращаем JSON
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }
//
//    // API для добавления продукта
//    @PostMapping
//    @ResponseBody // Указываем, что возвращаем JSON
//    public Product addProduct(@RequestBody Product product) {
//        return productService.saveProduct(product);
//    }
//
//    // Контроллер для отображения страницы всех продуктов
//    @GetMapping("/products")
//    public String showProductsPage() {
//        return "products"; // Имя вашего шаблона
//    }
//
//
////    @GetMapping("/product/{id}")
////    public String getProduct(@PathVariable Long id, Model model) {
////        Product product = productService.findById(id);
////        model.addAttribute("product", product);
////        return "single-product"; // Имя вашего шаблона
////    }
//}
