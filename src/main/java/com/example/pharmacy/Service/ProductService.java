package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.Product.ProductRequest;
import com.example.pharmacy.DTO.Product.ProductResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.ProductMapper;
import com.example.pharmacy.Model.Product;
import com.example.pharmacy.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;
    private final FileService fileService;

    // CREATE
    public ProductResponse create(ProductRequest req) {

        Product p = new Product();

        p.setProductName(req.getProductName());
        p.setProductPrice(req.getProductPrice());
        p.setDiscountPrice(req.getDiscountPrice());
        p.setStockQty(req.getStockQty());
        p.setExpiryDate(req.getExpiryDate());
        p.setProductStatus(req.getProductStatus());
        p.setProductDescription(req.getProductDescription());

        // IMAGE UPLOAD
        if (req.getImage() != null && !req.getImage().isEmpty()) {
            String fileName = fileService.saveFile(req.getImage());
            p.setImageUrl("/uploads/" + fileName);
        }

        return mapper.toResponse(repo.save(p));
    }

    // GET ALL
    public Page<ProductResponse> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable).map(mapper::toResponse);
    }

    // GET BY ID
    public ProductResponse getById(Long id) {

        Product p = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Product not found: " + id));

        return mapper.toResponse(p);
    }

    // UPDATE
    public ProductResponse update(Long id, ProductRequest req) {

        Product p = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Product not found: " + id));

        p.setProductName(req.getProductName());
        p.setProductPrice(req.getProductPrice());
        p.setDiscountPrice(req.getDiscountPrice());
        p.setStockQty(req.getStockQty());
        p.setExpiryDate(req.getExpiryDate());
        p.setProductStatus(req.getProductStatus());
        p.setProductDescription(req.getProductDescription());

        // replace image
        if (req.getImage() != null && !req.getImage().isEmpty()) {

            fileService.deleteFile(
                    p.getImageUrl() != null
                            ? p.getImageUrl().replace("/uploads/", "")
                            : null
            );

            String fileName = fileService.saveFile(req.getImage());
            p.setImageUrl("/uploads/" + fileName);
        }

        return mapper.toResponse(repo.save(p));
    }

    // DELETE
    public void delete(Long id) {

        Product p = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Product not found: " + id));

        fileService.deleteFile(
                p.getImageUrl() != null
                        ? p.getImageUrl().replace("/uploads/", "")
                        : null
        );

        repo.delete(p);
    }
}