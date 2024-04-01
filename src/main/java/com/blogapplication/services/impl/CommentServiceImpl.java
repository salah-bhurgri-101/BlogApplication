package com.blogapplication.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapplication.entities.Comment;
import com.blogapplication.entities.Post;
import com.blogapplication.exceptions.ResourceNotFoundException;
import com.blogapplication.payloads.CommentDto;
import com.blogapplication.repositories.CommentRepo;
import com.blogapplication.repositories.PostRepo;
import com.blogapplication.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = commentRepo.save(comment);
		
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment id", commentId));
		commentRepo.delete(comment);

	}

}
