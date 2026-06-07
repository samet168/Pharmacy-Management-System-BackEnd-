package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.User.UserRequest;
import com.example.pharmacy.DTO.User.UserResponse;
import com.example.pharmacy.Model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest req) {

        User user = new User();

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());
        user.setPassword(req.getPassword());

        if (req.getRole() != null) {
            user.setRole(req.getRole());
        }

        return user;
    }

    public UserResponse toResponse(User user) {

        UserResponse res = new UserResponse();

        res.setId(user.getId());
        res.setUsername(user.getUsername());
        res.setEmail(user.getEmail());
        res.setPhone(user.getPhone());
        res.setAddress(user.getAddress());
        res.setRole(user.getRole());
        res.setCreatedAt(user.getCreatedAt());

        return res;
    }

    public void update(User user, UserRequest req) {

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());

        if (req.getRole() != null) {
            user.setRole(req.getRole());
        }

        if (req.getPassword() != null &&
                !req.getPassword().isBlank()) {
            user.setPassword(req.getPassword());
        }
    }
}