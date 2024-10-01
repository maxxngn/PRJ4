//package com.example.fashion2;
//
//import com.example.fashion2.model.Cart;
//import com.example.fashion2.model.Product;
//import com.example.fashion2.service.CartService;
//import com.example.fashion2.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartController {
//    @Autowired
//    private CartService cartService;
//
//    @Autowired
//    private ProductService productService;
//
//    @PostMapping("/add/{productId}")
//    public void addToCart(@PathVariable Long productId) {
//        Product product = productService.getAllProducts().stream()
//                .filter(p -> p.getId().equals(productId))
//                .findFirst()
//                .orElse(null);
//        if (product != null) {
//            cartService.addToCart(product);
//        }
//    }
//
//    @GetMapping
//    public Cart getCart() {
//        return cartService.getCart();
//    }
//
//}
//
