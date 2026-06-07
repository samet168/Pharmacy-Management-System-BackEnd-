package com.example.pharmacy.DTO.Invoice;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceRequest {

    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    private BigDecimal taxAmount;

    private BigDecimal grandTotal;
}