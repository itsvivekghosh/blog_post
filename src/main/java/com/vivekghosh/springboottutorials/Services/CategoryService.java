package com.vivekghosh.springboottutorials.Services;

import java.util.List;

import com.vivekghosh.springboottutorials.dto.CategoryDTO;

public interface CategoryService {
	
	CategoryDTO addCategory(CategoryDTO categoryDto);
	
	CategoryDTO getCategory(Long categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId);

    void deleteCategory(Long categoryId);
}
