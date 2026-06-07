package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.Order.OrderRequest;
import com.example.pharmacy.DTO.Order.OrderResponse;
import com.example.pharmacy.Model.Order;
import com.example.pharmacy.Model.User;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest req, User user) {

        Order o = new Order();

        o.setUser(user);
        o.setTotalAmount(req.getTotalAmount());
        o.setOrderStatus(
                req.getOrderStatus() != null
                        ? req.getOrderStatus()
                        : "PENDING"
        );

        return o;
    }

    public OrderResponse toResponse(Order o) {

        OrderResponse r = new OrderResponse();

        r.setId(o.getId());
        r.setUserId(o.getUser().getId());
        r.setUsername(o.getUser().getUsername());
        r.setTotalAmount(o.getTotalAmount());
        r.setOrderStatus(o.getOrderStatus());
        r.setOrderDate(o.getOrderDate());

        return r;
    }
}