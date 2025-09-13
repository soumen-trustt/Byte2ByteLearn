package com.soumenprogramming.byte2byte.learn.user.management.controller;

import com.soumenprogramming.byte2byte.learn.user.management.dao.UserDaoService;
import com.soumenprogramming.byte2byte.learn.user.management.entity.UserRegistration;
import com.soumenprogramming.byte2byte.learn.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/users")
public class UserRegistrationController {

    @Autowired
    private UserDaoService userDaoService;

    @Autowired
    private EmailService emailService;

    private static final Logger LOG = LoggerFactory.getLogger(UserRegistrationController.class);

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> registerUser(@RequestBody Map<String, Object> request) {
        request.forEach((key, value) -> {
            System.out.println(key + ": " + (key.toLowerCase().contains("password") ? "[PROTECTED]" : value));
        });
        try {
            
            String username = (String) request.get("username");
            String email = (String) request.get("email");
            String password = (String) request.get("password");
            String fullName = (String) request.get("fullName");
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(request);
                LOG.debug("Request as JSON: " + json);
            } catch (Exception e) {
                LOG.debug("Could not convert request to JSON: " + e.getMessage());
            }
            
            // Validate required fields
            if (username == null || email == null || password == null) {
                String missingFields = "";
                if (username == null) missingFields += "username, ";
                if (email == null) missingFields += "email, ";
                if (password == null) missingFields += "password, ";
                if(fullName == null) missingFields += "fullName, ";
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Missing required fields: " + 
                        missingFields.replaceAll(", $", "")));
            }
            
            // Create and save user
            UserRegistration user = new UserRegistration();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setFullName(fullName);
            user.setCreatedAt(new java.util.Date());
            user.setUpdatedAt(new java.util.Date());
            UserRegistration savedUser = userDaoService.save(user);
            
            // Send registration success email
            try {
                emailService.sendRegistrationSuccessEmail(savedUser.getEmail(), savedUser.getUsername(), savedUser.getFullname(),savedUser.getPassword());
                LOG.info("Registration email sent successfully to user: {}", savedUser.getUsername());
            } catch (Exception emailException) {
                LOG.warn("Failed to send registration email to user: {} - {}", savedUser.getUsername(), emailException.getMessage());
                // Continue with registration even if email fails
            }
            
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                        "message", "User registered successfully",
                        "userId", savedUser.getId(),
                        "username", savedUser.getUsername()
                    ));
                    
        } catch (Exception e) {
            // Log the exception
            LOG.error("Error during registration: " + e.getMessage());
            e.printStackTrace();
            
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred during registration: " + e.getMessage()));
        }
    }

    @GetMapping(value = "/check-username", produces = "application/json")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam String username) {
        boolean isAvailable = !userDaoService.existsByUsername(username);
        return ResponseEntity.ok(Map.of("available", isAvailable));
    }

    @GetMapping(value = "/check-email", produces = "application/json")
    public ResponseEntity<?> checkEmailAvailability(@RequestParam String email) {
        boolean isAvailable = !userDaoService.existsByEmail(email);
        return ResponseEntity.ok(Map.of("available", isAvailable));
    }
}