package com.vivekghosh.springboottutorials.utils;

import lombok.Data;

@Data
public class PostComment {
	
	private Long commentId;
	
	private String commentBody;
	
	private Long commentUserId;
	
	private String commentUserName;
	
}
