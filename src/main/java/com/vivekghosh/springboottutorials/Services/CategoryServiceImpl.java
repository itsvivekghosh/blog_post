package com.vivekghosh.springboottutorials.Services;

import java.util.List;

import com.vivekghosh.springboottutorials.dto.CategoryDTO;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public CategoryDTO addCategory(CategoryDTO categoryDto) {
//		Category category = modelMapper.map(categoryDto, Category.class);
//        Category savedCategory = categoryRepository.save(category);
//        return modelMapper.map(savedCategory, CategoryDto.class);
		return null;
	}

	@Override
	public CategoryDTO getCategory(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCategory(Long categoryId) {
		// TODO Auto-generated method stub
		
	}
	
}
