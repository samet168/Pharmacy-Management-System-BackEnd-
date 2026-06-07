package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.OrderItem.OrderItemRequest;
import com.example.pharmacy.DTO.OrderItem.OrderItemResponse;
import com.example.pharmacy.Model.Order;
import com.example.pharmacy.Model.OrderItem;
import com.example.pharmacy.Model.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemMapper {

    public OrderItem toEntity(
            OrderItemRequest req,
            Order order,
            Product product
    ) {

        OrderItem item = new OrderItem();

        item.setOrder(order);
        item.setProduct(product);

        item.setQty(req.getQty());

        // price fallback from product if null
        BigDecimal price =
                req.getPrice() != null
                        ? req.getPrice()
                        : product.getProductPrice();

        item.setPrice(price);

        return item;
    }

    public OrderItemResponse toResponse(OrderItem item) {

        OrderItemResponse r = new OrderItemResponse();

        r.setId(item.getId());
        r.setOrderId(item.getOrder().getId());
        r.setProductId(item.getProduct().getId());
        r.setProductName(item.getProduct().getProductName());
        r.setQty(item.getQty());
        r.setPrice(item.getPrice());

        BigDecimal total =
                item.getPrice().multiply(
                        BigDecimal.valueOf(item.getQty())
                );

        r.setTotal(total);

        return r;
    }
}