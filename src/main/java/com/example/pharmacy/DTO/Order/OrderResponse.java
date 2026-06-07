package com.example.pharmacy.DTO.Order;

import com.example.pharmacy.Model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderResponse {

    private Long id;

    private Long userId;
    private String username;

    private BigDecimal totalAmount;
    private String orderStatus;
    private LocalDateTime orderDate;
}