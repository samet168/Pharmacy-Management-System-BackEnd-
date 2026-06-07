package com.example.pharmacy.DTO.Invoice;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceResponse {

    private Long id;

    private Long paymentId;
    private Long orderId;

    private String invoiceNumber;

    private BigDecimal taxAmount;
    private BigDecimal grandTotal;

    private LocalDateTime createdAt;
}