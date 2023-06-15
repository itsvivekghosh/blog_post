package com.vivekghosh.springboottutorials.Services.Comments;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vivekghosh.springboottutorials.Exceptions.BlogAPIException;
import com.vivekghosh.springboottutorials.Exceptions.ResourceNotFoundException;
import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.Services.Helpers.MappingEntities;
import com.vivekghosh.springboottutorials.dao.CommentRepository;
import com.vivekghosh.springboottutorials.dao.PostRepository;
import com.vivekghosh.springboottutorials.dao.UserRepository;
import com.vivekghosh.springboottutorials.dto.CommentDTO;
import com.vivekghosh.springboottutorials.entities.Comment;
import com.vivekghosh.springboottutorials.entities.Post;
import com.vivekghosh.springboottutorials.entities.UserProfile;


@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	private MappingEntities mappingEntities;
	
	public CommentServiceImpl() {
		this.mappingEntities = new MappingEntities();
	}
	
	@Override
	public CommentDTO createComment(Long post_id, CommentDTO commentDto) {

		Post post = postRepository.findById(post_id).orElseThrow(
			() ->  new ResourceNotFoundException("Post", "id", post_id)
		);
		UserProfile user = userRepository.findById(commentDto.getUserId()).orElseThrow(
			() ->  new ResourceNotFoundException("UserProfile", "id", commentDto.getUserId())
		);
		
		try {
			
			Comment comment = new Comment();
			comment.setCommentBody(commentDto.getCommentBody());
			comment.setPost(post);
			comment.setUserProfile(user);
			
			return mappingEntities.mapCommentEntityToCommentDTO(commentRepository.save(comment));
			
		} catch (Exception e) {
			System.err.println("Error occurred while saving/creating the comment, Error: "+ e.getMessage());
		}
		
		return null;
	}

	@Override
	public GenericPayload deleteUserPostCommentById(Long post_id, Long comment_id) {
		
		GenericPayload response = new GenericPayload();

		Post post = postRepository.findById(post_id).orElseThrow(
			() -> new ResourceNotFoundException("Post", "id", post_id)
        );
		
		Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", comment_id));
		
		try {
			if (comment != null) {
				
				if(!comment.getPost().getPostId().equals(post.getPostId())){
		            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
		        }
				
				commentRepository.delete(comment);
				
				response.setMessage(String.format("Comment Post with Id: `%s` deleted successfully!", comment.getCommentId()));
				response.setStatus(HttpStatus.OK);
				
				return response;
			}
			
		} catch (Exception e) {
			System.err.println("Error while deleting the Post Comment, Error: " + e.getMessage());
		}
		
		response.setMessage("Error while deleting the Post Comment!");
		response.setStatus(HttpStatus.NOT_MODIFIED);
		return response;
	}

	@Override
	public List<CommentDTO> findAllCommentsByPostId(Long post_id) {
		
		List<CommentDTO> commentsDTO = new LinkedList<>();
		List<Comment> comments = new LinkedList<>();
		
		try {
			// retrieve comments by postId
	        comments = commentRepository.findByPostPostId(post_id);
	        
	        return comments.stream().map(
        		comment -> mapCommentEntityToCommentDTO(comment)
    		).collect(Collectors.toList());
        
		} catch (Exception e) {
			System.err.println("Error while fetching the Post Comments by Post ID, Error:: " + e.getMessage());
		}
		
		return commentsDTO;
	}
	
	private CommentDTO mapCommentEntityToCommentDTO(Comment comment) {

        CommentDTO commentDto = new CommentDTO();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setCommentBody(comment.getCommentBody());
        commentDto.setUserId(comment.getUserProfile().getUserProfileId());
        commentDto.setUserName(comment.getUserProfile().getUserName());
        
        return  commentDto;
    }

}
