package com.example.pharmacy.DTO.OrderItem;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long id;

    private Long orderId;
    private Long productId;

    private String productName;

    private Integer qty;
    private BigDecimal price;

    private BigDecimal total; // qty * price
}