package com.blogapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.entities.Category;
import com.blogapplication.entities.Post;
import com.blogapplication.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title); 
	
//	@Query("select p from post p where p.title like : key ") version 5.6.6 hibernate
//	List<Post> searchByTitle(@Param("key") String title);
}
