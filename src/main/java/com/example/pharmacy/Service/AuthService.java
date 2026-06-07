package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.Auth.*;
import com.example.pharmacy.Model.User;
import com.example.pharmacy.Repository.UserRepository;
import com.example.pharmacy.Security.JwtUtil;
import com.example.pharmacy.Security.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final TokenBlacklist blacklist;

    public String register(RegisterRequest req) {

        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(User.Role.CASHIER);

        repo.save(u);

        return "Register success";
    }

    public AuthResponse login(LoginRequest req) {

        User u = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtUtil.generateToken(
                u.getEmail(),
                u.getRole().name()
        );

        return new AuthResponse(
                token,
                u.getUsername(),
                u.getEmail(),
                u.getRole().name()
        );
    }
    public String logout(String token) {
        blacklist.add(token);
        return "Logout success";
    }
}