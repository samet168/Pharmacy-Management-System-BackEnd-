package com.example.pharmacy.Controller;

import com.example.pharmacy.DTO.Auth.*;
import com.example.pharmacy.Service.AuthService;
import com.example.pharmacy.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest req) {
        return ApiResponse.ok(service.register(req));
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody LoginRequest req) {
        return ApiResponse.ok(service.login(req));
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return ApiResponse.error("Token missing");
        }

        String token = header.substring(7);

        return ApiResponse.ok(service.logout(token));
    }
}