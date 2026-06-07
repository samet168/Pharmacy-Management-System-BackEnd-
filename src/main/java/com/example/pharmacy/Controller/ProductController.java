package com.example.pharmacy.Controller;

import com.example.pharmacy.DTO.Product.ProductRequest;
import com.example.pharmacy.Service.ProductService;
import com.example.pharmacy.Util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    // CREATE (multipart)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> create(@Valid @ModelAttribute ProductRequest req) {
        return ApiResponse.create(service.create(req));
    }
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> createJson(@Valid @RequestBody ProductRequest req) {
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
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> update(
            @PathVariable Long id,
            @Valid @ModelAttribute ProductRequest req
    ) {
        return ApiResponse.ok(service.update(id, req));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Product deleted successfully");
    }
}