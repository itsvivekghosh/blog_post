/**
 * 
 */
package com.vivekghosh.springboottutorials.Services.Authentication;

import com.vivekghosh.springboottutorials.dto.LoginDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;


public interface AuthenticationService {
	
	public String userLogin(LoginDTO loginDto);

	public UserDTO userRegister(UserDTO userDto);
	
}
