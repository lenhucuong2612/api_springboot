package com.example.my_demo.controller;

import com.example.my_demo.payloads.ApiResponse;
import com.example.my_demo.payloads.CategoryDto;
import com.example.my_demo.services.impl.CategoryImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryImpl categoryImpl;
    //create
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory=this.categoryImpl.createCategory(categoryDto);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtoList=this.categoryImpl.getAllCategory();
        return new ResponseEntity<>(categoryDtoList,HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable int id){
        CategoryDto categoryDtoUpdate=this.categoryImpl.updateCategory(id,categoryDto);
        return new ResponseEntity<>(categoryDtoUpdate,HttpStatus.OK);
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<CategoryDto> findOneCategory(@PathVariable Integer id){
        CategoryDto categoryDto=this.categoryImpl.getCategory(id);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        this.categoryImpl.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Delete successfully",true),HttpStatus.OK);
    }
}
