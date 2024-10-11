package com.example.fashion2;

import com.example.fashion2.service.ProductService;
import com.example.fashion2.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String Index(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "user/home/index";
    }
}
