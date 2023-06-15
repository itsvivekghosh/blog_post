package com.vivekghosh.springboottutorials.Services.UserProfile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vivekghosh.springboottutorials.Exceptions.BlogAPIException;
import com.vivekghosh.springboottutorials.Exceptions.ResourceNotFoundException;
import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.Services.Helpers.MappingEntities;
import com.vivekghosh.springboottutorials.dao.RoleRepository;
import com.vivekghosh.springboottutorials.dao.UserRepository;
import com.vivekghosh.springboottutorials.dto.PasswordDTO;
import com.vivekghosh.springboottutorials.dto.PostDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;
import com.vivekghosh.springboottutorials.entities.Role;
import com.vivekghosh.springboottutorials.entities.UserProfile;
import com.vivekghosh.springboottutorials.utils.BlogUtils;

@Service("userService")
public class UserServiceImpl implements UserService {
	

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private MappingEntities mappingEntities;
	
	private BlogUtils blogUtils;
	
	public UserServiceImpl() {
		this.mappingEntities = new MappingEntities();
		this.blogUtils = new BlogUtils();
	}
	
	private UserDTO sendUserDTOResponse(UserProfile user) {
		
		// convert entity to DTO
        UserDTO userResponse = mappingEntities.mapUserProfileToUserProfileDTO(user);
        return userResponse;
	}
	
	private UserProfile sendUserProfileFromUserProfileDTOResponse(UserDTO userDto) {
		
		// convert entity to DTO
        UserProfile userResponse = mappingEntities.mapUserProfileDTOToUserProfileEntity(userDto);
        return userResponse;
	}
	
	private List<UserDTO> sendListUserDTOResponse(List<UserProfile> listUsers) {

		// convert List entity to List DTO
		List<UserDTO> userDTOList = listUsers.stream().map(user -> mappingEntities.mapUserProfileToUserProfileDTO(user)).collect(Collectors.toList());
		
		return userDTOList;
	}
	
	@Override
	public UserDTO createUserProfile(UserDTO userDto) {

		UserDTO userResponse =  null;
		UserProfile user = new UserProfile();
		
		try {
			if(userRepository.existsByUserName(userDto.getUserName())) {
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User Name is already exists!.");
	        }
	
	        // add check for email exists in database
	        if(userRepository.existsByEmailAddress(userDto.getEmailAddress())) {
	            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email Address is already exists!.");
	        }
	        
			// convert DTO to entity
	        user.setUserProfileId(userDto.getUserProfileId());
	        user.setUserName(userDto.getUserName());
	        user.setFullName(userDto.getFullName());
	        user.setEmailAddress(userDto.getEmailAddress());
	        user.setPhoneNumber(userDto.getPhoneNumber());
	        user.setAadharNumber(userDto.getAadharNumber());
	        user.setUserAddress(userDto.getUserAddress());
	        
	        
	        // saving the encoded password in DB
	        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
	        user.setPassword(encodedPassword);
	        
			Set<Role> roles = new HashSet<Role>();
	        Role userRole = roleRepository.findByName("ROLE_USER").get();
	        roles.add(userRole);
	        user.setRoles(roles);
	        
	        UserProfile newUser = userRepository.save(user);
			userResponse = mappingEntities.mapUserProfileToUserProfileDTO(newUser);
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
        return userResponse;
        
	}

	@Override
	public UserDTO getProfileById(Long id) {
		
		UserProfile user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserProfile", "id", id));
		
		return sendUserDTOResponse(user);
	}

	@Override
	public UserDTO getProfileByUserName(String user_name) {
		UserProfile user = userRepository.findByUserName(user_name);
		
		return sendUserDTOResponse(user);
	}
	
	@Override
	public UserDTO getProfileByEmailAddress(String email) {
		
		UserProfile user = userRepository.findByEmailAddress(email);
		
		return sendUserDTOResponse(user);
	}

	@Override
	public UserDTO getProfileByAadharNumber(String adhaar_number) {
		UserProfile user = userRepository.findByAadharNumber(adhaar_number);
		
		return sendUserDTOResponse(user);
	}

	@Override
	public List<UserDTO> getAllUserProfiles() {
		List<UserProfile> listUsers = userRepository.findAll();
		
		List<UserDTO> userDTOList = listUsers.stream().map(user -> mappingEntities.mapUserProfileToUserProfileDTO(user)).collect(Collectors.toList());

		return userDTOList;
	}

	@Override
	public UserDTO updateUserProfile(UserDTO userDto, Long id) {
		
		Boolean isUpdated = false;
		
		try {
			// get post by id from the database
	        UserProfile userProfile = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	        
	        if (userProfile != null) {
	        
		        if (!blogUtils.checkBothFields(userProfile.getUserName(), userDto.getUserName())) {
		        	userProfile.setUserName(userDto.getUserName());
			        isUpdated = true;
				}
		        if (!blogUtils.checkBothFields(userProfile.getFullName(), userDto.getFullName())) {
		        	userProfile.setFullName(userDto.getFullName());
		        	isUpdated = true;
				}
		        
		        if (!blogUtils.checkBothFields(userProfile.getEmailAddress(), userDto.getEmailAddress())) {
		        	userProfile.setEmailAddress(userDto.getEmailAddress());
		        	isUpdated = true;
		        }
		        
		        if (!blogUtils.checkBothFields(userProfile.getPhoneNumber(), userDto.getPhoneNumber())) {
		        	userProfile.setPhoneNumber(userDto.getPhoneNumber());
		        	isUpdated = true;
		        }
		        
		        if (!blogUtils.checkBothFields(userProfile.getAadharNumber(), userDto.getAadharNumber())) {
		        	userProfile.setAadharNumber(userDto.getAadharNumber());
		        	isUpdated = true;
		        }
		        
		        if (!blogUtils.checkBothFields(userProfile.getUserAddress(), userDto.getUserAddress())) {
		        	userProfile.setUserAddress(userDto.getUserAddress());
		        	isUpdated = true;
				}
		        
	        }
	        
	        if (isUpdated == true) {  
	        	userProfile.setLastProfileUpdated(LocalDateTime.now());
				UserProfile updatedUserProfile = userRepository.save(userProfile);
	
		        return sendUserDTOResponse(updatedUserProfile);
	        } 
	        
	        return sendUserDTOResponse(userProfile);
	        
		} catch (Exception e) {
			System.err.println("Excetion occurred while updating profile, Error: " + e.getMessage());
		}
		
		return userDto;
	}

	@Override
	public UserDTO deleteUserProfile(Long id) {
		 // get post by id from the database
        UserProfile userProfile = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        userRepository.delete(userProfile);
        
        return sendUserDTOResponse(userProfile);
	}

	@Override
	public List<UserDTO> getProfileBySearchedKeyword(String keyword) {
		List<UserProfile> users = userRepository.getProfileBySearchedKeyword(keyword);
		
		return sendListUserDTOResponse(users);
	}

	@Override
	public GenericPayload updateUserPassword(PasswordDTO passwordDTO, Long id) {
		
		GenericPayload response = new GenericPayload();
		
		try {
			
			UserProfile user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserProfile", "id", id));
			
			if (user != null) {
				
				if (!passwordEncoder.matches(passwordDTO.getPassword(), user.getPassword())) {
				
					user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
					user.setLastPasswordUpdated(LocalDateTime.now());
					
					userRepository.save(user);
					
					response.setMessage("Password changed successfully!");
					response.setStatus(HttpStatus.OK);
					
					return response;
				} else {
					
					response.setMessage("New Password is same as the last password, type a new password!");
					response.setStatus(HttpStatus.NOT_MODIFIED);
					
					return response;
					
				}
			}
			
		} catch (Exception e) {
			System.err.println("Error occurred while changing password, Error: " + e.getMessage());
		} 
		
		return response;
	}
	
}

/*
{
  "aadharNumber": "574738145325",
  "userAddress": "Dakpathar, Dehradun",
  "phoneNumber": 6396474255,
  "emailAddress": "soapmactevis1@gmail.com",
  "fullName": "Vivek Kumar Ghosh",
  "userName": "itsvivekghosh",
  "password": "Vivek@1999"
}
 * */
