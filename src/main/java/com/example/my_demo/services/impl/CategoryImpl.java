package com.example.my_demo.services.impl;

import com.example.my_demo.entity.Category;
import com.example.my_demo.exceptions.ResourceNotFoundException;
import com.example.my_demo.payloads.CategoryDto;
import com.example.my_demo.repositories.CateRepo;
import com.example.my_demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CateRepo cateRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.dtoCategory(categoryDto);
        Category categorySave=this.cateRepo.save(category);
        return this.categoryToDo(categorySave);
    }

    @Override
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        Category categoryFind=this.cateRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",id));
        categoryFind.setCategoryTitle(categoryDto.getCategoryDtoTitle());
        categoryFind.setCategoryDescription(categoryDto.getCategoryDtoDescription());
        Category categorySave=this.cateRepo.save(categoryFind);
        return categoryToDo(categorySave);
    }

    @Override
    public void deleteCategory(Integer id) {
        this.cateRepo.delete(cateRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",id)));
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category categoryFind= this.cateRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
        return categoryToDo(categoryFind);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList=this.cateRepo.findAll();
        List<CategoryDto> categoryDtoList=new ArrayList<>();
        for(Category a: categoryList){
            categoryDtoList.add(categoryToDo(a));
        }
        return categoryDtoList;
    }
    public Category dtoCategory(CategoryDto categoryDto){
        Category category=new Category();
        category.setCategoryId(categoryDto.getId());
        category.setCategoryTitle(categoryDto.getCategoryDtoTitle());
        category.setCategoryDescription(categoryDto.getCategoryDtoDescription());
        return category;
    }
    public CategoryDto categoryToDo(Category category){
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(category.getCategoryId());
        categoryDto.setCategoryDtoTitle(category.getCategoryTitle());
        categoryDto.setCategoryDtoDescription(category.getCategoryDescription());
        return categoryDto;
    }
}
