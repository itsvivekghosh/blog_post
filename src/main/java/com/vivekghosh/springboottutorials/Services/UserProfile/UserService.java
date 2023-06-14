package com.vivekghosh.springboottutorials.Services.UserProfile;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.vivekghosh.springboottutorials.dto.UserDTO;

public interface UserService {
	
	public UserDTO createUserProfile(UserDTO userDto);
	
	public List<UserDTO> getAllUserProfiles();
	
	public UserDTO getProfileById(Long id);
	
	public UserDTO getProfileByUserName(String user_name);
	
	public UserDTO getProfileByEmailAddress(String user_name);
	
	public UserDTO getProfileByAadharNumber(String adhaar_number);
	
	public UserDTO updateUserProfile(UserDTO userDto, Long id);

	public UserDTO deleteUserProfile(Long id);
	
	public List<UserDTO> getProfileBySearchedKeyword(String keyword);
}
