package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.User.UserRequest;
import com.example.pharmacy.DTO.User.UserResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.UserMapper;
import com.example.pharmacy.Model.User;
import com.example.pharmacy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    // CREATE
    public UserResponse create(UserRequest req) {

        User user = mapper.toEntity(req);

        user.setPassword(
                passwordEncoder.encode(req.getPassword())
        );

        return mapper.toResponse(repo.save(user));
    }

    // GET ALL (pagination + sort)
    public Page<UserResponse> getAll(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable)
                .map(mapper::toResponse);
    }

    // GET BY ID
    public UserResponse getById(Long id) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException(
                                "User not found with id: " + id));

        return mapper.toResponse(user);
    }

    // UPDATE
    public UserResponse update(Long id, UserRequest req) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException(
                                "User not found with id: " + id));

        mapper.update(user, req);

        // Encode password only when provided
        if (req.getPassword() != null &&
                !req.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(req.getPassword())
            );
        }

        return mapper.toResponse(repo.save(user));
    }

    // DELETE
    public void delete(Long id) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException(
                                "User not found with id: " + id));

        repo.delete(user);
    }

    // FILTER + SEARCH + PAGINATION + SORT
    public Page<UserResponse> filter(
            String username,
            String email,
            User.Role role,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<User> spec = Specification.unrestricted();

        if (username != null && !username.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("username")),
                            "%" + username.toLowerCase() + "%"
                    ));
        }

        if (email != null && !email.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("email")),
                            "%" + email.toLowerCase() + "%"
                    ));
        }

        if (role != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("role"), role));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}