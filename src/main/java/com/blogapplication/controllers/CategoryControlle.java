package com.blogapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapplication.exceptions.HttpRequestMethodNotSupportedException;
import com.blogapplication.payloads.ApiResponse;
import com.blogapplication.payloads.CategoryDto;
import com.blogapplication.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin("*")
public class CategoryControlle {

	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	//update
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto , @PathVariable("categoryId") Integer cId){
		CategoryDto updateCategory = categoryService.updateCategory(categoryDto, cId);
//		return ResponseEntity.ok(updateCategory);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	
	//delete
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cId){
		categoryService.deleteCategory(cId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully", true) , HttpStatus.OK);
	}
	
	//get
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer cId){
		CategoryDto categoryDto = categoryService.getCategory(cId);
		//return ResponseEntity.ok(categoryDto);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> categories = categoryService.getCategories();
		return ResponseEntity.ok(categories);
		
	}
	
	//sdsdsd
	@PutMapping("/")
	public HttpRequestMethodNotSupportedException updateUrlnotFound(){
		throw new HttpRequestMethodNotSupportedException("url");
		
		
	}
	@DeleteMapping("/")
	public HttpRequestMethodNotSupportedException deleteUrlNotFound(){
		throw new HttpRequestMethodNotSupportedException("url");
		
		
	}
	
}
