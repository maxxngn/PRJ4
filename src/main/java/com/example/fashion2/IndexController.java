//package com.example.fashion2;
//import org.springframework.ui.Model;
//import com.example.fashion2.model.Product;
//import com.example.fashion2.model.Subscriber;
//import com.example.fashion2.service.ProductService;
//import com.example.fashion2.service.SubscriberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//
//@Controller
//public class IndexController {
//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private SubscriberService subscriberService;
//
//    @GetMapping("/")
//    public String showHomePage(Model model) {
//        // Получаем все продукты и добавляем их в модель
//        List<Product> products = productService.getAllProducts();
//        model.addAttribute("products", products);
//
//        // Добавляем объект для подписки
//        model.addAttribute("subscriber", new Subscriber());
//
//        return "index"; // Имя вашего шаблона
//    }
//
//    @PostMapping("/newsletter")
//    public String subscribe(@ModelAttribute("subscriber") Subscriber subscriber, Model model) {
//        // Сохраняем подписчика
//        subscriberService.subscribe(subscriber);
//
//        // Добавляем сообщение в модель
//        model.addAttribute("message", "Вы успешно подписались на рассылку с 30% скидкой! Проверьте ваш email для подтверждения.");
//
//        // Возвращаемся к главной странице
//        return "user/home/index"; // Замените на имя вашего шаблона, если оно отличается
//    }
//}
