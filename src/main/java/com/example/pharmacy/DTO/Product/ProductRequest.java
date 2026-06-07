//package com.example.pharmacy.DTO.Product;
//
//import jakarta.validation.constraints.*;
//import lombok.Data;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Data
//public class ProductRequest {
//
//    @NotBlank(message = "Product name is required")
//    private String productName;
//
//    private MultipartFile image;
//
//    @NotNull(message = "Price is required")
//    @DecimalMin(value = "0.0", message = "Price must be >= 0")
//    private BigDecimal productPrice;
//
//    private BigDecimal discountPrice;
//
//    @NotNull(message = "Stock is required")
//    @Min(value = 0, message = "Stock must be >= 0")
//    private Integer stockQty;
//
//    private LocalDate expiryDate;
//
//    private String productStatus;
//
//    private String productDescription;
//
//    private Long categoryId;
//    private Long brandId;
//}

package com.example.pharmacy.DTO.Product;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat; // បន្ថែម Import នេះ
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    private MultipartFile image;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be >= 0")
    private BigDecimal productPrice;

    private BigDecimal discountPrice;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be >= 0")
    private Integer stockQty;

    // បន្ថែម Annotation នេះដើម្បីដោះស្រាយកំហុស 400
    @DateTimeFormat(pattern = "yyyy-M-d")
    private LocalDate expiryDate;

    private String productStatus;

    private String productDescription;

    private Long categoryId;
    private Long brandId;
}