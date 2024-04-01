package com.blogapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
