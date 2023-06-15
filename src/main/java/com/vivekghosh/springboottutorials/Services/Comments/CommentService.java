package com.vivekghosh.springboottutorials.Services.Comments;

import java.util.List;

import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.dto.CommentDTO;



public interface CommentService {

	public CommentDTO createComment(Long comment_id, CommentDTO commentDto);
	
	public GenericPayload deleteUserPostCommentById(Long post_id, Long comment_id);
	
	public List<CommentDTO> findAllCommentsByPostId(Long post_id);
}
