package com.example.pharmacy.Controller;

import com.example.pharmacy.DTO.User.UserRequest;
import com.example.pharmacy.DTO.User.UserResponse;
import com.example.pharmacy.Model.User;
import com.example.pharmacy.Service.UserService;
import com.example.pharmacy.Util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    // CREATE
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRequest req) {
        return ResponseEntity.ok(ApiResponse.create(service.create(req)));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(service.getAll(page, size, sortBy, sortDir))
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.getById(id)));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest req
    ) {
        return ResponseEntity.ok(ApiResponse.ok(service.update(id, req)));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("User deleted successfully"));
    }

    // FILTER
    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) User.Role role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Page<UserResponse> result = service.filter(
                username, email, role,
                page, size, sortBy, sortDir
        );

        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}