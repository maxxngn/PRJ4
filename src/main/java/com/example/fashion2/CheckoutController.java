package com.example.fashion2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CheckoutController {
    @GetMapping("/checkout")
    public String checkoutPage() {
        // Возвращаем представление (HTML файл), например "checkout.html"
        return "checkout"; // Это файл checkout.html в папке resources/templates
    }
}
