/**
 * 
 */
package com.vivekghosh.springboottutorials.Services.Helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vivekghosh.springboottutorials.dto.UserDTO;
import com.vivekghosh.springboottutorials.entities.UserProfile;


public class MappingEntities {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// convert Entity into DTO
    public UserDTO mapToDTO(UserProfile user) {

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

    // convert DTO to entity
    public UserProfile mapToEntity(UserDTO userDto) {
    	
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
}
