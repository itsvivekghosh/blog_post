package com.vivekghosh.springboottutorials.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.Services.Comments.CommentService;
import com.vivekghosh.springboottutorials.dto.CommentDTO;



@RestController
@RequestMapping("/api/v1/post/{post_id}/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping()
	public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(
			@PathVariable(name = "post_id") Long post_id) {
		
		List<CommentDTO> commentDto = commentService.findAllCommentsByPostId(post_id);
		return new ResponseEntity<List<CommentDTO>>(
			commentDto, HttpStatus.CREATED
		);
		
	}
	
	@PostMapping()
	public ResponseEntity<CommentDTO> createCommentByPostId(
			@PathVariable(name = "post_id") Long post_id,
			@Valid @RequestBody CommentDTO commentDTO) {
		
		CommentDTO commentDto = commentService.createComment(post_id, commentDTO);
		return new ResponseEntity<CommentDTO>(
			commentDto, HttpStatus.CREATED
		);
		
	}
	
	@DeleteMapping("/{comment_id}")
    public ResponseEntity<GenericPayload> deleteUserPostComment(
    		@PathVariable(name = "comment_id") Long comment_id,
    		@PathVariable(name = "post_id") Long post_id ){

       return new ResponseEntity<GenericPayload>(
    		   commentService.deleteUserPostCommentById(post_id, comment_id), HttpStatus.OK
	   );
       
    }
}
