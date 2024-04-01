package com.blogapplication.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.entities.Category;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.CategoryDto;
import com.blogapplication.repositories.CategoryRepo;
import com.blogapplication.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = categoryDtoToCategory(categoryDto);
		Category savedCategory = categoryRepo.save(category);
		return categoryToCategoryDto(savedCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = categoryRepo.save(category);
		CategoryDto categoryDto1 = categoryToCategoryDto(updatedCategory);
		
		return categoryDto1;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		return categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> categoriesDto = categories.stream().map((category)-> categoryToCategoryDto(category)).collect(Collectors.toList());
		return categoriesDto;
	}
	
	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	public CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
