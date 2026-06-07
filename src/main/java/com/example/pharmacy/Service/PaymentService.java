package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.Payment.PaymentRequest;
import com.example.pharmacy.DTO.Payment.PaymentResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.PaymentMapper;
import com.example.pharmacy.Model.Order;
import com.example.pharmacy.Model.Payment;
import com.example.pharmacy.Repository.OrderRepository;
import com.example.pharmacy.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;
    private final OrderRepository orderRepo;
    private final PaymentMapper mapper;

    // CREATE PAYMENT
    public PaymentResponse create(PaymentRequest req) {

        Order order = orderRepo.findById(req.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotException("Order not found"));

        Payment payment = mapper.toEntity(req, order);

        // auto update paid time if status = PAID
        if ("PAID".equalsIgnoreCase(payment.getPaymentStatus())) {
            payment.setPaidAt(LocalDateTime.now());
        }

        return mapper.toResponse(repo.save(payment));
    }

    // GET ALL
    public Page<PaymentResponse> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable)
                .map(mapper::toResponse);
    }

    // GET BY ID
    public PaymentResponse getById(Long id) {

        Payment p = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Payment not found"));

        return mapper.toResponse(p);
    }

    // UPDATE
    public PaymentResponse update(Long id, PaymentRequest req) {

        Payment p = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Payment not found"));

        Order order = orderRepo.findById(req.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotException("Order not found"));

        p.setOrder(order);
        p.setPaymentMethod(req.getPaymentMethod());
        p.setPaymentStatus(req.getPaymentStatus());
        p.setTransactionRef(req.getTransactionRef());

        if ("PAID".equalsIgnoreCase(req.getPaymentStatus())) {
            p.setPaidAt(LocalDateTime.now());
        }

        return mapper.toResponse(repo.save(p));
    }

    // DELETE
    public void delete(Long id) {

        Payment p = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Payment not found"));

        repo.delete(p);
    }

    // FILTER
    public Page<PaymentResponse> filter(
            String status,
            String method,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Payment> spec = Specification.unrestricted();

        if (status != null && !status.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("paymentStatus"), status));
        }

        if (method != null && !method.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("paymentMethod")),
                            "%" + method.toLowerCase() + "%"
                    ));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}