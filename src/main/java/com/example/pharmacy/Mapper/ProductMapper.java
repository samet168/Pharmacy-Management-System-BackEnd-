package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.Product.ProductResponse;
import com.example.pharmacy.Model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // នេះគឺជា Base URL ដែលនឹងត្រូវបូកបញ្ចូលជាមួយឈ្មោះរូបភាព
    private final String BASE_URL = "http://localhost:8080/uploads/";

    public ProductResponse toResponse(Product p) {

        ProductResponse r = new ProductResponse();

        // Mapping ទិន្នន័យមូលដ្ឋាន
        r.setId(p.getId());
        r.setProductName(p.getProductName());
        r.setProductPrice(p.getProductPrice());
        r.setDiscountPrice(p.getDiscountPrice());
        r.setStockQty(p.getStockQty());
        r.setExpiryDate(p.getExpiryDate());
        r.setProductStatus(p.getProductStatus());
        r.setProductDescription(p.getProductDescription());

        // Mapping រូបភាព៖ ដោះស្រាយបញ្ហា URL ជាន់គ្នា
        if (p.getImageUrl() != null && !p.getImageUrl().isEmpty()) {
            // ដកពាក្យ "/uploads/" ចេញពី Database string បើមាន ដើម្បីកុំឱ្យជាន់ជាមួយ BASE_URL
            String fileName = p.getImageUrl().replace("/uploads/", "");
            r.setImageUrl(BASE_URL + fileName);
        } else {
            r.setImageUrl(null);
        }

        // Mapping ទំនាក់ទំនង (Relationships)
        if (p.getCategory() != null)
            r.setCategoryName(p.getCategory().getCategoryName());

        if (p.getBrand() != null)
            r.setBrandName(p.getBrand().getBrandName());

        return r;
    }
}