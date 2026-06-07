package com.example.pharmacy.DTO.User;

import com.example.pharmacy.Model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private User.Role role;
    private LocalDateTime createdAt;
}