package com.example.pharmacy.Controller;

import com.example.pharmacy.DTO.Payment.PaymentRequest;
import com.example.pharmacy.Service.PaymentService;
import com.example.pharmacy.Util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    // CREATE
    @PostMapping
    public ApiResponse<?> create(@Valid @RequestBody PaymentRequest req) {
        return ApiResponse.create(service.create(req));
    }

    // GET ALL
    @GetMapping
    public ApiResponse<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Page<?> data = service.getAll(page, size, sortBy, sortDir);
        return ApiResponse.ok(data);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<?> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ApiResponse<?> update(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequest req
    ) {
        return ApiResponse.ok(service.update(id, req));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Payment deleted successfully");
    }

    // FILTER
    @GetMapping("/filter")
    public ApiResponse<?> filter(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String method,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Page<?> data = service.filter(status, method, page, size, sortBy, sortDir);
        return ApiResponse.ok(data);
    }
}