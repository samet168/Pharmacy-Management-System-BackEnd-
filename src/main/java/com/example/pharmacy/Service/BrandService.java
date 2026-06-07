package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.Brand.BrandRequest;
import com.example.pharmacy.DTO.Brand.BrandResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.BrandMapper;
import com.example.pharmacy.Model.Brand;
import com.example.pharmacy.Repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository repo;
    private final BrandMapper mapper;

    // CREATE
    public BrandResponse create(BrandRequest req) {

        Brand brand = mapper.toEntity(req);

        return mapper.toResponse(repo.save(brand));
    }

    // GET ALL
    public Page<BrandResponse> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable).map(mapper::toResponse);
    }

    // GET BY ID
    public BrandResponse getById(Long id) {

        Brand brand = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Brand not found: " + id));

        return mapper.toResponse(brand);
    }

    // UPDATE
    public BrandResponse update(Long id, BrandRequest req) {

        Brand brand = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Brand not found: " + id));

        mapper.update(brand, req);

        return mapper.toResponse(repo.save(brand));
    }

    // DELETE
    public void delete(Long id) {

        Brand brand = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Brand not found: " + id));

        repo.delete(brand);
    }

    // FILTER (SEARCH by brandName)
    public Page<BrandResponse> filter(
            String brandName,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Brand> spec = Specification.unrestricted();

        if (brandName != null && !brandName.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("brandName")),
                            "%" + brandName.toLowerCase() + "%"
                    ));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}