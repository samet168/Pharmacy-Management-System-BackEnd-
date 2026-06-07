package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.Order.OrderRequest;
import com.example.pharmacy.DTO.Order.OrderResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.OrderMapper;
import com.example.pharmacy.Model.Order;
import com.example.pharmacy.Model.User;
import com.example.pharmacy.Repository.OrderRepository;
import com.example.pharmacy.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final OrderMapper mapper;

    // CREATE
    public OrderResponse create(OrderRequest req) {

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() ->
                        new ResourceNotException("User not found: " + req.getUserId()));

        Order order = mapper.toEntity(req, user);

        return mapper.toResponse(orderRepo.save(order));
    }

    // GET ALL
    public Page<OrderResponse> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return orderRepo.findAll(pageable)
                .map(mapper::toResponse);
    }

    // GET BY ID
    public OrderResponse getById(Long id) {

        Order o = orderRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Order not found: " + id));

        return mapper.toResponse(o);
    }

    // UPDATE
    public OrderResponse update(Long id, OrderRequest req) {

        Order o = orderRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Order not found: " + id));

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() ->
                        new ResourceNotException("User not found: " + req.getUserId()));

        o.setUser(user);
        o.setTotalAmount(req.getTotalAmount());
        o.setOrderStatus(req.getOrderStatus());

        return mapper.toResponse(orderRepo.save(o));
    }

    // DELETE
    public void delete(Long id) {

        Order o = orderRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Order not found: " + id));

        orderRepo.delete(o);
    }

    // FILTER
    public Page<OrderResponse> filter(
            String status,
            Long userId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Order> spec = Specification.unrestricted();

        if (status != null && !status.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("orderStatus"), status));
        }

        if (userId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId));
        }

        return orderRepo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}