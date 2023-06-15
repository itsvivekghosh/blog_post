package com.vivekghosh.springboottutorials.Services.Posts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vivekghosh.springboottutorials.Exceptions.ResourceNotFoundException;
import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.Services.Helpers.MappingEntities;
import com.vivekghosh.springboottutorials.dao.PostRepository;
import com.vivekghosh.springboottutorials.dao.UserRepository;
import com.vivekghosh.springboottutorials.dto.PostDTO;
import com.vivekghosh.springboottutorials.entities.Post;
import com.vivekghosh.springboottutorials.entities.UserProfile;
import com.vivekghosh.springboottutorials.utils.BlogUtils;


@Service("postService")
public class PostServiceImpl implements PostService {
	
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private MappingEntities mappingEntities;
	
	private BlogUtils blogUtils;
	
	public PostServiceImpl() {
		this.mappingEntities = new MappingEntities();
		this.blogUtils = new BlogUtils();
	}
	
	
	@Override
	public PostDTO createPost(PostDTO postDto) {

		try {
			
			Post post = mappingEntities.mapPostDTOToPostEntity(postDto);
			
			UserProfile user = userRepository.findById(postDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("UserProfile", "id", postDto.getUserId()));
			
			if (user != null) {
				
				post.setUserProfile(user);
				Post newPost = postRepository.save(post);
				PostDTO newPostDto = mappingEntities.mapPostEntityToPostDTO(newPost);
				
				return newPostDto;
			} else {
				return postDto;
			}
			
		} catch (Exception e) {
			System.err.println("Error Occurred while creating Post, Error:: "+ e.getMessage());
		}
		return null;
	}

	@Override
	public PostDTO findPostById(Long id) {
		
		PostDTO postDto = new PostDTO();
		
		try {
			
			Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
			
			if (post != null) {
				postDto = mappingEntities.mapPostEntityToPostDTO(post);
				
				return postDto;
			}
			
		} catch (Exception e) {
			System.err.println("Error Occurred while fetching the post by ID: Post, Error:: "+ e.getMessage());
		}
		return postDto;
	}


	@Override
	public PostDTO updatedPostById(Long id, PostDTO postDto) {
		try {
			Boolean isUpdated = false;
			Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
			
			if (post != null) {
				
				if (!blogUtils.checkBothFields(postDto.getPostTitle(), post.getPostTitle())) {
		        	post.setPostTitle(postDto.getPostTitle());
		        	isUpdated = true;
				}
				
				if (!blogUtils.checkBothFields(postDto.getPostContent(), post.getPostContent())) {
		        	post.setPostContent(postDto.getPostContent());
		        	isUpdated = true;
				}
				
				if (!blogUtils.checkBothFields(postDto.getPostDescription(), post.getPostDescription())) {
		        	post.setPostDescription(postDto.getPostDescription());
		        	isUpdated = true;
				}

				if (post.getIsUpdated() == false && isUpdated)
		        	post.setIsUpdated(true);
				
			}
			
			if (isUpdated == true) { 
				
				post.setPostUpdatedAt(LocalDateTime.now());
				Post updatedPost = postRepository.save(post);
				PostDTO updatedPostDto = mappingEntities.mapPostEntityToPostDTO(updatedPost);
				
				return updatedPostDto;
				
			} else {
				
				return mappingEntities.mapPostEntityToPostDTO(post);
				
			}
			
		} catch (Exception e) {
			System.err.println("Error Occurred while fetching the post by ID: Post, Error:: "+ e.getMessage());
		}
		return postDto;
	}

	@Override
	public PostDTO findPostByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public GenericPayload deleteUserPostById(Long id) {
		
		GenericPayload response = new GenericPayload();
		try {
			
			Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
			
			if (post != null) {
				
				postRepository.delete(post);
				
				response.setMessage(String.format("Post with title: `%s` deleted successfully!", post.getPostTitle()));
				response.setStatus(HttpStatus.OK);
				
				return response;
			}
			
		} catch (Exception e) {
			System.err.println("Error while deleting the Post, Error: " + e.getMessage());
		}
		
		return response;
	}
	
}
