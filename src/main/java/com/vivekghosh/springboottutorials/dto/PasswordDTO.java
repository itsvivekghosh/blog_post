package com.vivekghosh.springboottutorials.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
	
	private String usernameOrEmail;
	
    private String password;
	
}
