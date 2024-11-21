//
//package com.example.fashion2;
//import com.example.fashion2.service.SubscriberService;
//import com.example.fashion2.model.Subscriber;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/newsletter")
//public class NewsletterController {
//
//    @Autowired
//    private SubscriberService subscriberService;
//
//    @GetMapping
//    public String showForm(Model model) {
//        model.addAttribute("subscriber", new Subscriber());
//        return "newsletter-form"; // имя шаблона
//    }
//
//    // @PostMapping
//    // public String subscribe(Subscriber subscriber, Model model) {
//    //     subscriberService.subscribe(subscriber);
//    //     model.addAttribute("message", "Вы успешно подписались на рассылку с 30% скидкой!");
//    //     return "subscription-success"; // имя шаблона
//    // }
//}
