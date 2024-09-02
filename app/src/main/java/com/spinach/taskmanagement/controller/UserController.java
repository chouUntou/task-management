package com.spinach.taskmanagement.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spinach.taskmanagement.dto.RegisterRequestDTO;
import com.spinach.taskmanagement.entity.Role;
import com.spinach.taskmanagement.entity.User;
import com.spinach.taskmanagement.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("API is working！!");
    }

    @Autowired
    private UserService userService;

    @PostMapping("/regist")
    public ResponseEntity<?> registUser(@Validated @RequestBody RegisterRequestDTO registerRequestDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        
        try {
            User newUser = userService.registUser(registerRequestDTO);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");
        String roleStr = payload.getOrDefault("role", "member");
        Role role = Role.valueOf(roleStr.toUpperCase());

        String token = userService.login(username, password, role);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
