package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.Brand.BrandRequest;
import com.example.pharmacy.DTO.Brand.BrandResponse;
import com.example.pharmacy.Model.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public Brand toEntity(BrandRequest req) {

        Brand brand = new Brand();
        brand.setBrandName(req.getBrandName());

        return brand;
    }

    public BrandResponse toResponse(Brand brand) {

        BrandResponse res = new BrandResponse();

        res.setId(brand.getId());
        res.setBrandName(brand.getBrandName());

        return res;
    }

    public void update(Brand brand, BrandRequest req) {
        brand.setBrandName(req.getBrandName());
    }
}