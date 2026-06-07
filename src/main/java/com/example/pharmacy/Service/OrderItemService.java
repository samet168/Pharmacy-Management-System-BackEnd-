package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.OrderItem.OrderItemRequest;
import com.example.pharmacy.DTO.OrderItem.OrderItemResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.OrderItemMapper;
import com.example.pharmacy.Model.Order;
import com.example.pharmacy.Model.OrderItem;
import com.example.pharmacy.Model.Product;
import com.example.pharmacy.Repository.OrderItemRepository;
import com.example.pharmacy.Repository.OrderRepository;
import com.example.pharmacy.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repo;
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final OrderItemMapper mapper;

    // CREATE
    public OrderItemResponse create(OrderItemRequest req) {

        Order order = orderRepo.findById(req.getOrderId())
                .orElseThrow(() ->
                        new ResourceNotException("Order not found"));

        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() ->
                        new ResourceNotException("Product not found"));

        OrderItem item = mapper.toEntity(req, order, product);

        return mapper.toResponse(repo.save(item));
    }

    // GET ALL
    public Page<OrderItemResponse> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable).map(mapper::toResponse);
    }

    // GET BY ID
    public OrderItemResponse getById(Long id) {

        OrderItem item = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("OrderItem not found"));

        return mapper.toResponse(item);
    }

    // UPDATE
    public OrderItemResponse update(Long id, OrderItemRequest req) {

        OrderItem item = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("OrderItem not found"));

        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() ->
                        new ResourceNotException("Product not found"));

        item.setProduct(product);
        item.setQty(req.getQty());

        item.setPrice(
                req.getPrice() != null
                        ? req.getPrice()
                        : product.getProductPrice()
        );

        return mapper.toResponse(repo.save(item));
    }

    // DELETE
    public void delete(Long id) {

        OrderItem item = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("OrderItem not found"));

        repo.delete(item);
    }

    // FILTER (by orderId or productId)
    public Page<OrderItemResponse> filter(
            Long orderId,
            Long productId,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<OrderItem> spec = Specification.unrestricted();

        if (orderId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("order").get("id"), orderId));
        }

        if (productId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("product").get("id"), productId));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}