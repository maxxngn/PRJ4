package com.example.fashion2;

import com.example.fashion2.model.Subscriber;
import com.example.fashion2.service.SubscriberService; // Убедитесь, что этот сервис есть
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewsletterController {

    @Autowired
    private SubscriberService subscriberService;
    @GetMapping("/")
    public String showIndex(Model model) {
        model.addAttribute("subscriber", new Subscriber());
        return "index"; // Имя вашего шаблона
    }
    @PostMapping("/newsletter") // Обрабатываем отправку формы с главной страницы
    public String subscribe(@ModelAttribute("subscriber") Subscriber subscriber, Model model) {
        // Сохраняем подписчика
        subscriberService.subscribe(subscriber);

        // Добавляем сообщение в модель
        model.addAttribute("message", "Вы успешно подписались на рассылку с 30% скидкой! Проверьте ваш email для подтверждения.");

        // Возвращаемся к главной странице
        return "index"; // Замените на имя вашего шаблона, если оно отличается
    }
}
