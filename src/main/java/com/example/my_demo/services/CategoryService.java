package com.example.my_demo.services;

import com.example.my_demo.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    //create
    public CategoryDto createCategory(CategoryDto categoryDto);
    //update
    CategoryDto updateCategory(Integer id, CategoryDto categoryDto);
    //delete
    public void deleteCategory(Integer id);
    //get
    public CategoryDto getCategory(Integer categoryId);
    //get all
    public List<CategoryDto> getAllCategory();
}
