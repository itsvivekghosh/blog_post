package com.vivekghosh.springboottutorials.dto;

import lombok.Data;



@Data
public class CommentDTO {
	
	private Long commentId;
	
	private String commentBody;
	
	private Long userId;
	
	private String userName;
	
}
