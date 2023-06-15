package com.vivekghosh.springboottutorials.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.Services.Posts.PostService;
import com.vivekghosh.springboottutorials.dto.PostDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;


@RestController
@RequestMapping("/api/v1/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping(path = "/create_", consumes={ "application/json" })
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDto) {
			
		return new ResponseEntity<PostDTO>(
			postService.createPost(postDto), HttpStatus.CREATED
		);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id) {
		
		return new ResponseEntity<PostDTO>(
				postService.findPostById(id), HttpStatus.OK
		);
	}
	
	@PutMapping(path="/{id}")
	public ResponseEntity<PostDTO> updatePostById(@Valid @RequestBody PostDTO postDto, @PathVariable(name = "id") Long id) {
		
		return new ResponseEntity<PostDTO>(
				postService.updatedPostById(id, postDto), HttpStatus.OK
		);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<GenericPayload> deleteUserPost(@PathVariable(name = "id") Long id){

       return new ResponseEntity<GenericPayload>(
    		   postService.deleteUserPostById(id), HttpStatus.OK
	   );
    }
}
