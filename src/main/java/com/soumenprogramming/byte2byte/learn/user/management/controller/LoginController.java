package com.soumenprogramming.byte2byte.learn.user.management.controller;

import com.soumenprogramming.byte2byte.learn.user.management.dao.UserDaoService;
import com.soumenprogramming.byte2byte.learn.user.management.entity.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@SuppressWarnings("unchecked")
public class LoginController {

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, Object> request) {
        String identifier = (String) request.get("username");
        String password = (String) request.get("password");

        if (identifier == null || identifier.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Username/Email and password are required."));
        }
        UserRegistration user = userDaoService.findByUsername(identifier);
        if(!identifier.equals(user.getUsername()) && !password.equals(user.getPassword()) ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username/email or password."));
        }
        return ResponseEntity.ok(
                Map.of(
                        "message", "Login successful.",
                        "userId", user.getId(),
                        "username", user.getUsername(),
                        "email", user.getEmail(),
                        "fullName", user.getFullname()
                )
        );
    }
}
