package com.example.fashion2.controller;

import com.example.fashion2.model.User;
import com.example.fashion2.service.UserService;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.register(user);
            registeredUser.setPassword(null);
            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<User> user = userService.login(username, password);
        if (user.isPresent()) {
            User loggedInUser = user.get();
<<<<<<< HEAD
            loggedInUser.setPassword(null);  //
=======
            loggedInUser.setPassword(null);
>>>>>>> ec0d1e2f780980105112e885be5c157bd4be78e1
            return ResponseEntity.ok(loggedInUser);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

}