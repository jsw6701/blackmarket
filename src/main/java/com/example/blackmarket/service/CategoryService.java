package com.example.blackmarket.service;



import com.example.blackmarket.dto.requeset.CategoryGetDto;
import com.example.blackmarket.model.Category;

import java.util.List;

public interface CategoryService {

    Category findCategoryByName(CategoryGetDto categoryGetDto);

    Category findById(Long id);

    List<Category> findAll();
}
