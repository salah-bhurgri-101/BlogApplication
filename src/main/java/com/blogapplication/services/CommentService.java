package com.blogapplication.services;

import com.blogapplication.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto , Integer postId);
	void deleteComment(Integer commentId);
}
