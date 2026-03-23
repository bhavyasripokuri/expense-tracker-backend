package com.expense.tracker.controller;

import com.expense.tracker.dto.AuthRequest;
import com.expense.tracker.dto.AuthResponse;
import com.expense.tracker.model.User;
import com.expense.tracker.security.JwtUtil;
import com.expense.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User user = userService.register(
                request.getEmail(),
                request.getPassword(),
                request.getEmail()
            );
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new AuthResponse(token, user.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userService.findByEmail(request.getEmail());

        if (userOpt.isEmpty() || !userService.checkPassword(request.getPassword(), userOpt.get().getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(userOpt.get().getEmail());
        return ResponseEntity.ok(new AuthResponse(token, userOpt.get().getEmail()));
    }
}