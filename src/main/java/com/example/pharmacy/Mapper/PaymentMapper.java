package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.Payment.PaymentRequest;
import com.example.pharmacy.DTO.Payment.PaymentResponse;
import com.example.pharmacy.Model.Order;
import com.example.pharmacy.Model.Payment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest req, Order order) {

        Payment p = new Payment();

        p.setOrder(order);
        p.setPaymentMethod(req.getPaymentMethod());
        p.setPaymentStatus(
                req.getPaymentStatus() != null
                        ? req.getPaymentStatus()
                        : "PENDING"
        );
        p.setTransactionRef(req.getTransactionRef());
        p.setPaidAt(LocalDateTime.now());

        return p;
    }

    public PaymentResponse toResponse(Payment p) {

        PaymentResponse r = new PaymentResponse();

        r.setId(p.getId());
        r.setOrderId(p.getOrder().getId());
        r.setOrderStatus(p.getOrder().getOrderStatus());

        r.setPaymentMethod(p.getPaymentMethod());
        r.setPaymentStatus(p.getPaymentStatus());
        r.setTransactionRef(p.getTransactionRef());
        r.setPaidAt(p.getPaidAt());

        return r;
    }
}