package com.vivekghosh.springboottutorials.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
	
	private String usernameOrEmail;
	
    private String password;
    
}
