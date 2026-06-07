package com.example.pharmacy.Mapper;

import com.example.pharmacy.DTO.Category.CategoryRequest;
import com.example.pharmacy.DTO.Category.CategoryResponse;
import com.example.pharmacy.Model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest req) {

        Category c = new Category();
        c.setCategoryName(req.getCategoryName());

        return c;
    }

    public CategoryResponse toResponse(Category c) {

        CategoryResponse r = new CategoryResponse();

        r.setId(c.getId());
        r.setCategoryName(c.getCategoryName());

        return r;
    }

    public void update(Category c, CategoryRequest req) {
        c.setCategoryName(req.getCategoryName());
    }
}