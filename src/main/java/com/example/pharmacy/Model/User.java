package com.example.pharmacy.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String phone;
    private String address;
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.CASHIER;


    public enum Role {
        ADMIN,
        CASHIER,

    }

    private LocalDateTime createdAt = LocalDateTime.now();
}