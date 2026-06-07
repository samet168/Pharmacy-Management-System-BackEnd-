package com.example.pharmacy.DTO.Order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    private String orderStatus;
}