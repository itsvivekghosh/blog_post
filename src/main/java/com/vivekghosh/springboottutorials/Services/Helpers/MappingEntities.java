/**
 * 
 */
package com.vivekghosh.springboottutorials.Services.Helpers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vivekghosh.springboottutorials.dto.CommentDTO;
import com.vivekghosh.springboottutorials.dto.PostDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;
import com.vivekghosh.springboottutorials.entities.Comment;
import com.vivekghosh.springboottutorials.entities.Post;
import com.vivekghosh.springboottutorials.entities.UserProfile;
import com.vivekghosh.springboottutorials.utils.LikedUser;
import com.vivekghosh.springboottutorials.utils.PostComment;


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
    	
    	Set<UserProfile> postLikedUsers = post.getPostLikedUsers();
    	Set<LikedUser> likedUsers = new HashSet<>();
    	if (postLikedUsers != null) {

        	
	    	postLikedUsers.forEach(user -> {
	    		
	    		LikedUser likedUser = new LikedUser();
	    		likedUser.setUserId(user.getUserProfileId());
	    		likedUser.setUserName(user.getUserName());
	    		
	    		likedUsers.add(likedUser);
	    	});
	    	
    	}
    	postDto.setPostLikedUsers(likedUsers);
    	
    	Collection<Comment> comments = post.getComments();
		Set<PostComment> postComments = new HashSet<PostComment>();
    	if (comments != null) {
    		
    		
    		comments.forEach(comment -> {
    			
    			PostComment postComment = new PostComment();
    			postComment.setCommentId(comment.getCommentId());
    			postComment.setCommentBody(comment.getCommentBody());
    			postComment.setCommentUserId(comment.getUserProfile().getUserProfileId());
    			postComment.setCommentUserName(comment.getUserProfile().getUserName());
    			
        		postComments.add(postComment);
        		
    		});
    		
    	}

		postDto.setPostComments(postComments);
		
    	return postDto;
    	
    }
    
    public CommentDTO mapCommentEntityToCommentDTO(Comment comment) {
    	
    	CommentDTO commentDTO = new CommentDTO();
    	commentDTO.setCommentBody(comment.getCommentBody());
    	commentDTO.setCommentId(comment.getCommentId());
    	commentDTO.setUserId(comment.getUserProfile().getUserProfileId());
    	commentDTO.setUserName(comment.getUserProfile().getUserName());
    	
    	return commentDTO;
    	
    }
}
