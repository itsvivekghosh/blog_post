package com.vivekghosh.springboottutorials.Services.Posts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vivekghosh.springboottutorials.Exceptions.ResourceNotFoundException;
import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.Services.Helpers.MappingEntities;
import com.vivekghosh.springboottutorials.dao.PostRepository;
import com.vivekghosh.springboottutorials.dao.UserRepository;
import com.vivekghosh.springboottutorials.dto.PostDTO;
import com.vivekghosh.springboottutorials.dto.PostLikeDTO;
import com.vivekghosh.springboottutorials.entities.Post;
import com.vivekghosh.springboottutorials.entities.UserProfile;
import com.vivekghosh.springboottutorials.utils.BlogUtils;
import com.vivekghosh.springboottutorials.utils.LikedUser;


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
		
		return postDto;
	}

	@Override
	public PostDTO findPostById(Long id) {
		
		PostDTO postDto = new PostDTO();
		
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		if (post != null) {
			
			postDto = mappingEntities.mapPostEntityToPostDTO(post);
			
			return postDto;
		}
		
		return postDto;
	}


	@Override
	public PostDTO updatedPostById(Long id, PostDTO postDto) {
		
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		try {
			
			Boolean isUpdated = false;
			
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
		
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		try {
			
			
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


	@Override
	public PostDTO likePostByPostId(Long post_id, PostLikeDTO postLikeDto) {

		Post post = postRepository.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
		
		UserProfile user = userRepository.findById(postLikeDto.getUserId()).orElseThrow(
			() -> new ResourceNotFoundException("UserProfile", "ID", postLikeDto.getUserId())
		);
	
		try {
			
			if (post != null && user != null) {
				
				Set<UserProfile> postLikedUsers = post.getPostLikedUsers();
				
				// if the user is not liked the 
				// post then increase the like by 1;
				if (!checkIfTheUserAlreadyLiked(postLikeDto.getUserId(), postLikedUsers)) {
					post.setPostLikes(post.getPostLikes() + 1);
				}
				
				postLikedUsers.add(user);
				
				
				Post newPost = postRepository.save(post);
				
				return mappingEntities.mapPostEntityToPostDTO(newPost);
			}
			
			
		} catch (Exception e) {
			System.err.println(
				String.format("Error found when liking a post id : `%s` , Error : `%s`", post_id, e.getMessage() )
			); 
		}
		
		return mappingEntities.mapPostEntityToPostDTO(post);
	}
	
	Boolean checkIfTheUserAlreadyLiked(Long user_id, Set<UserProfile> likedUsers) {
		
		if (likedUsers != null && user_id != null) {
			
			for (UserProfile user : likedUsers) {
				if (user.getUserProfileId().equals(user_id)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
