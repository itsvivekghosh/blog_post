package com.vivekghosh.springboottutorials.Services.Posts;

import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.dto.PostDTO;


public interface PostService {
	
	public PostDTO createPost(PostDTO postDto);
	
	public PostDTO findPostById(Long id);
	
	public PostDTO findPostByTitle(String title);
	
	public PostDTO updatedPostById(Long id, PostDTO postDto);
	
	public GenericPayload deleteUserPostById(Long id);
	
}
