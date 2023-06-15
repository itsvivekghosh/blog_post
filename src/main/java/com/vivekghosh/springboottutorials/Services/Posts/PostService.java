package com.vivekghosh.springboottutorials.Services.Posts;

import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.dto.PostDTO;
import com.vivekghosh.springboottutorials.dto.PostLikeDTO;


public interface PostService {
	
	public PostDTO createPost(PostDTO postDto);
	
	public PostDTO findPostById(Long id);
	
	public PostDTO findPostByTitle(String title);
	
	public PostDTO updatedPostById(Long id, PostDTO postDto);
	
	public PostDTO likePostByPostId(Long id, PostLikeDTO postLikeDto);
	
	public GenericPayload deleteUserPostById(Long id);
	
}
