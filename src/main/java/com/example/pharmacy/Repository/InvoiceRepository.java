package com.example.pharmacy.Repository;

import com.example.pharmacy.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvoiceRepository extends
        JpaRepository<Invoice, Long>,
        JpaSpecificationExecutor<Invoice> {
}