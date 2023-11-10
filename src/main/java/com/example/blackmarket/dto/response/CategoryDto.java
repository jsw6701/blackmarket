package com.example.blackmarket.dto.response;

import com.example.blackmarket.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    private String name;

    public CategoryDto(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
