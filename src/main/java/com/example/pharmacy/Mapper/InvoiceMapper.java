package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.Invoice.InvoiceRequest;
import com.example.pharmacy.DTO.Invoice.InvoiceResponse;
import com.example.pharmacy.Model.Invoice;
import com.example.pharmacy.Model.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InvoiceMapper {

    public Invoice toEntity(InvoiceRequest req, Payment payment) {

        Invoice inv = new Invoice();

        inv.setPayment(payment);

        inv.setInvoiceNumber(
                "INV-" + System.currentTimeMillis()
        );

        inv.setTaxAmount(req.getTaxAmount());
        inv.setGrandTotal(req.getGrandTotal());

        inv.setCreatedAt(LocalDateTime.now());

        return inv;
    }

    public InvoiceResponse toResponse(Invoice inv) {

        InvoiceResponse r = new InvoiceResponse();

        r.setId(inv.getId());
        r.setPaymentId(inv.getPayment().getId());
        r.setOrderId(inv.getPayment().getOrder().getId());

        r.setInvoiceNumber(inv.getInvoiceNumber());
        r.setTaxAmount(inv.getTaxAmount());
        r.setGrandTotal(inv.getGrandTotal());
        r.setCreatedAt(inv.getCreatedAt());

        return r;
    }
}