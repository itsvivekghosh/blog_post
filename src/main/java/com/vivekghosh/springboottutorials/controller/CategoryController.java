package com.vivekghosh.springboottutorials.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vivekghosh.springboottutorials.Services.CategoryService;
import com.vivekghosh.springboottutorials.dto.CategoryDTO;

public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDto) {
		
        CategoryDTO savedCategory = categoryService.addCategory(categoryDto);
        
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        
    }
}
