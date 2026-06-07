package com.example.pharmacy.DTO.Product;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductResponse {

    private Long id;
    private String productName;
    private String imageUrl;
    private BigDecimal productPrice;
    private BigDecimal discountPrice;
    private Integer stockQty;
    private LocalDate expiryDate;
    private String productStatus;
    private String productDescription;

    private String categoryName;
    private String brandName;
}