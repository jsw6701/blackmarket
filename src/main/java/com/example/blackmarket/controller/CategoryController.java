package com.example.blackmarket.controller;


import com.example.blackmarket.dto.requeset.CategoryGetDto;
import com.example.blackmarket.dto.response.CategoryDto;
import com.example.blackmarket.model.Category;
import com.example.blackmarket.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 목록 조회")
    @GetMapping("/category/readAll")
    public List<CategoryDto> readAll(){
        List<Category> categoryList = this.categoryService.findAll();
        List<CategoryDto> categoryDtoList = categoryList.stream().map(CategoryDto::new).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Operation(summary = "카테고리 조회")
    @GetMapping("/category/{categoryId}")
    public CategoryDto readById(Long categoryId){
        Category category = this.categoryService.findById(categoryId);
        return new CategoryDto(category);
    }

    @Operation(summary = "카테고리 이름으로 조회")
    @GetMapping("/category/findByName")
    public CategoryDto findByName(CategoryGetDto categoryGetDto){
        Category category = this.categoryService.findCategoryByName(categoryGetDto);
        return new CategoryDto(category);
    }
}
