package com.example.pharmacy.Repository;

import com.example.pharmacy.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends
        JpaRepository<Payment, Long>,
        JpaSpecificationExecutor<Payment> {
}