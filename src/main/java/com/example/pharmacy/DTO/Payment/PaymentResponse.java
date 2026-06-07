package com.example.pharmacy.DTO.Payment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponse {

    private Long id;

    private Long orderId;
    private String orderStatus;

    private String paymentMethod;
    private String paymentStatus;
    private String transactionRef;

    private LocalDateTime paidAt;
}