package com.example.pharmacy.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(unique = true)
    private String invoiceNumber;
    private BigDecimal taxAmount;
    private BigDecimal grandTotal;
    private LocalDateTime createdAt = LocalDateTime.now();
}
