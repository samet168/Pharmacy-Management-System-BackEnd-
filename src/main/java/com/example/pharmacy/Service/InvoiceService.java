package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.Invoice.InvoiceRequest;
import com.example.pharmacy.DTO.Invoice.InvoiceResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.InvoiceMapper;
import com.example.pharmacy.Model.Invoice;
import com.example.pharmacy.Model.Payment;
import com.example.pharmacy.Repository.InvoiceRepository;
import com.example.pharmacy.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repo;
    private final PaymentRepository paymentRepo;
    private final InvoiceMapper mapper;

    // CREATE
    public InvoiceResponse create(InvoiceRequest req) {

        Payment payment = paymentRepo.findById(req.getPaymentId())
                .orElseThrow(() ->
                        new ResourceNotException("Payment not found"));

        Invoice inv = mapper.toEntity(req, payment);

        return mapper.toResponse(repo.save(inv));
    }

    // GET ALL
    public Page<InvoiceResponse> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable)
                .map(mapper::toResponse);
    }

    // GET BY ID
    public InvoiceResponse getById(Long id) {

        Invoice inv = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Invoice not found"));

        return mapper.toResponse(inv);
    }

    // UPDATE
    public InvoiceResponse update(Long id, InvoiceRequest req) {

        Invoice inv = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Invoice not found"));

        Payment payment = paymentRepo.findById(req.getPaymentId())
                .orElseThrow(() ->
                        new ResourceNotException("Payment not found"));

        inv.setPayment(payment);
        inv.setTaxAmount(req.getTaxAmount());
        inv.setGrandTotal(req.getGrandTotal());

        return mapper.toResponse(repo.save(inv));
    }

    // DELETE
    public void delete(Long id) {

        Invoice inv = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Invoice not found"));

        repo.delete(inv);
    }

    // FILTER
    public Page<InvoiceResponse> filter(
            String invoiceNumber,
            Long paymentId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Invoice> spec = Specification.unrestricted();

        if (invoiceNumber != null && !invoiceNumber.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("invoiceNumber")),
                            "%" + invoiceNumber.toLowerCase() + "%"
                    ));
        }

        if (paymentId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("payment").get("id"), paymentId));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}