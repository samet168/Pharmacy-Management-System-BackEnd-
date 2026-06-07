package com.example.pharmacy.DTO.User;

import com.example.pharmacy.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Invalid email")
    private String email;

    private String phone;
    private String address;

    @NotBlank(message = "Password is required")
    private String password;

    private User.Role role;
}