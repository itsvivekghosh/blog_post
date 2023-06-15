/**
 * 
 */
package com.vivekghosh.springboottutorials.Services.Helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vivekghosh.springboottutorials.dto.PostDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;
import com.vivekghosh.springboottutorials.entities.Post;
import com.vivekghosh.springboottutorials.entities.UserProfile;


public class MappingEntities {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// convert User Profile Entity into User Profile DTO
    public UserDTO mapUserProfileToUserProfileDTO(UserProfile user) {

        UserDTO userDto = new UserDTO();
        
    	try {
	        
	        userDto.setUserProfileId(user.getUserProfileId());
	        userDto.setUserName(user.getUserName());
	        userDto.setFullName(user.getFullName());
	        userDto.setEmailAddress(user.getEmailAddress());
	        userDto.setPhoneNumber(user.getPhoneNumber());
	        userDto.setAadharNumber(user.getAadharNumber());
	        userDto.setUserAddress(user.getUserAddress());
	        
    	} catch (Exception e) {
    		System.err.println(e);
    	}
        
        return userDto;
    }

    // convert User Profile DTO to User Entity
    public UserProfile mapUserProfileDTOToUserProfileEntity(UserDTO userDto) {
    	
    	UserProfile user = new UserProfile();
        
        user.setUserProfileId(userDto.getUserProfileId());
        user.setUserName(userDto.getUserName());
        user.setFullName(userDto.getFullName());
        user.setEmailAddress(userDto.getEmailAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAadharNumber(userDto.getAadharNumber());
        user.setUserAddress(userDto.getUserAddress());
        user.setPassword(userDto.getPassword());
        
        return user;
    }
    
    public Post mapPostDTOToPostEntity(PostDTO postDto) {
    	
    	Post post = new Post();
    	
    	post.setPostTitle(postDto.getPostTitle());
    	post.setPostContent(postDto.getPostContent());
    	post.setPostDescription(postDto.getPostDescription());
    	
    	return post;
    	
    }
    
    
    public PostDTO mapPostEntityToPostDTO(Post post) {
    	
    	PostDTO postDto = new PostDTO();

    	postDto.setId(post.getPostId());
    	postDto.setPostTitle(post.getPostTitle());
    	postDto.setPostContent(post.getPostContent());
    	postDto.setPostDescription(post.getPostDescription());
    	postDto.setPostLikes(post.getPostLikes());
    	postDto.setPostCreatedAt(post.getPostCreatedAt());
    	postDto.setIsUpdated(post.getIsUpdated());
    	postDto.setPostUpdatedAt(post.getPostUpdatedAt());
    	postDto.setUserId(post.getUserProfile().getUserProfileId());
    	postDto.setUserName(post.getUserProfile().getUserName());
    	
    	return postDto;
    	
    }
}
