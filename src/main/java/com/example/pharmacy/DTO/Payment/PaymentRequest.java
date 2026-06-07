package com.example.pharmacy.DTO.Payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    private String paymentMethod;
    private String paymentStatus;
    private String transactionRef;
}