package com.blogapplication.services;

import java.util.List;

import com.blogapplication.payloads.PostDto;
import com.blogapplication.payloads.PostResponse;

public interface PostService {

	//create
	PostDto createPost (PostDto postDto , Integer userId , Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto , Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//getAllPost
	PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy, String sortDir);
	
	//getSinglePost
	PostDto getPostById(Integer postId);
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//search Posts
	List<PostDto> searchPosts(String keyword);
	
}
