package com.example.blackmarket.service;


import com.example.blackmarket.dto.requeset.CategoryGetDto;
import com.example.blackmarket.model.Category;
import com.example.blackmarket.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryByName(CategoryGetDto categoryGetDto) {
        return categoryRepository.findByName(categoryGetDto.getName());
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 없습니다."));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
