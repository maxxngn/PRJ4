// package com.example.fashion2.controller;

// import com.example.fashion2.repository.DashboardRepository;
// import com.example.fashion2.repository.UserRepository;
// import com.example.fashion2.model.User;
// import com.example.fashion2.model.Comment;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/dashboard")
// public class DashboardController {

//     @Autowired
//     private DashboardRepository dashboardRepository;

//     @Autowired
//     private UserRepository userRepository;

//     // API to count total orders
//     @GetMapping("/total-orders")
//     public long getTotalOrders() {
//         return dashboardRepository.countByIdNotNull();
//     }

//     // API to count total price (custom method)
//     @GetMapping("/total-price")
//     public Integer getTotalPrice() {
//         return dashboardRepository.countTotalPrice();
//     }

//     // API to count total users with a specific role (USER)
//     @GetMapping("/total-users")
//     public long getTotalUsers() {
//         return userRepository.countByRole(User.Role.USER);
//     }

//     // API to count total comments (assuming this query works with Comment entity)
//     @GetMapping("/total-comments")
//     public long getTotalComments() {
//         return dashboardRepository.countByIdNotNullAndProductIdNotNull(new Comment());
//     }
// }
