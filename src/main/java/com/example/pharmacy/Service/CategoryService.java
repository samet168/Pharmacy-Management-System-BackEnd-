package com.example.pharmacy.Service;

import com.example.pharmacy.DTO.Category.CategoryRequest;
import com.example.pharmacy.DTO.Category.CategoryResponse;
import com.example.pharmacy.Exception.ResourceNotException;
import com.example.pharmacy.Mapper.CategoryMapper;
import com.example.pharmacy.Model.Category;
import com.example.pharmacy.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repo;
    private final CategoryMapper mapper;

    // CREATE
    public CategoryResponse create(CategoryRequest req) {

        Category c = mapper.toEntity(req);

        return mapper.toResponse(repo.save(c));
    }

    // GET ALL
    public Page<CategoryResponse> getAll(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repo.findAll(pageable).map(mapper::toResponse);
    }

    // GET BY ID
    public CategoryResponse getById(Long id) {

        Category c = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Category not found: " + id));

        return mapper.toResponse(c);
    }

    // UPDATE
    public CategoryResponse update(Long id, CategoryRequest req) {

        Category c = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Category not found: " + id));

        mapper.update(c, req);

        return mapper.toResponse(repo.save(c));
    }

    // DELETE
    public void delete(Long id) {

        Category c = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotException("Category not found: " + id));

        repo.delete(c);
    }

    // FILTER
    public Page<CategoryResponse> filter(
            String categoryName,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Category> spec = Specification.unrestricted();

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("categoryName")),
                            "%" + categoryName.toLowerCase() + "%"
                    ));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}