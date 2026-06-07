package com.example.pharmacy.Repository;

import com.example.pharmacy.Model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BrandRepository extends
        JpaRepository<Brand, Long>,
        JpaSpecificationExecutor<Brand> {
}