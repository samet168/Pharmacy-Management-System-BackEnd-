package com.example.pharmacy.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String imageUrl; // សម្រាប់រក្សាទុក Link រូបភាព
    private BigDecimal productPrice;
    private BigDecimal discountPrice;
    private Integer stockQty;
    private LocalDate expiryDate;
    private String productStatus;
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}