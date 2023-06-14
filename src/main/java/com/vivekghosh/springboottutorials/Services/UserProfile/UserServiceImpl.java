package com.vivekghosh.springboottutorials.Services.UserProfile;

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
import com.vivekghosh.springboottutorials.Services.Helpers.MappingEntities;
import com.vivekghosh.springboottutorials.dao.RoleRepository;
import com.vivekghosh.springboottutorials.dao.UserRepository;
import com.vivekghosh.springboottutorials.dto.UserDTO;
import com.vivekghosh.springboottutorials.entities.Role;
import com.vivekghosh.springboottutorials.entities.UserProfile;

@Service("userService")
public class UserServiceImpl implements UserService {
	

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	MappingEntities mappingEntities;
	
	public UserServiceImpl() {
		mappingEntities = new MappingEntities();
	}
	
	private UserDTO sendUserDTOResponse(UserProfile user) {
		
		// convert entity to DTO
        UserDTO userResponse = mappingEntities.mapToDTO(user);
        return userResponse;
	}
	
	private UserProfile sendUserProfileFromUserProfileDTOResponse(UserDTO userDto) {
		
		// convert entity to DTO
        UserProfile userResponse = mappingEntities.mapToEntity(userDto);
        return userResponse;
	}
	
	private List<UserDTO> sendListUserDTOResponse(List<UserProfile> listUsers) {

		// convert List entity to List DTO
		List<UserDTO> userDTOList = listUsers.stream().map(user -> mappingEntities.mapToDTO(user)).collect(Collectors.toList());
		
		return userDTOList;
	}
	
	@Override
	public UserDTO createUserProfile(UserDTO userDto) {
		
		if(userRepository.existsByUserName(userDto.getUserName())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "User Name is already exists!.");
        }

        // add check for email exists in database
        if(userRepository.existsByEmailAddress(userDto.getEmailAddress())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email Address is already exists!.");
        }
        
		// convert DTO to entity
		UserProfile user = mappingEntities.mapToEntity(userDto);
		
		Set<Role> roles = new HashSet<Role>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        
        UserProfile newUser = userRepository.save(user);

        return sendUserDTOResponse(newUser);
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
		
		List<UserDTO> userDTOList = listUsers.stream().map(user -> mappingEntities.mapToDTO(user)).collect(Collectors.toList());

		return userDTOList;
	}

	@Override
	public UserDTO updateUserProfile(UserDTO userDto, Long id) {
		
		// get post by id from the database
        UserProfile userProfile = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        
        if (!checkBothFields(userProfile.getUserName(), userDto.getUserName()))
        	userProfile.setUserName(userDto.getUserName());
        
        if (!checkBothFields(userProfile.getFullName(), userDto.getFullName()))
        	userProfile.setFullName(userDto.getFullName());
        
        if (!checkBothFields(userProfile.getEmailAddress(), userDto.getEmailAddress()))
        	userProfile.setEmailAddress(userDto.getEmailAddress());
        
        if (!checkBothFields(userProfile.getPhoneNumber(), userDto.getPhoneNumber()))
        	userProfile.setPhoneNumber(userDto.getPhoneNumber());
        
        if (!checkBothFields(userProfile.getAadharNumber(), userDto.getAadharNumber()))
        	userProfile.setAadharNumber(userDto.getAadharNumber());
        
        if (!checkBothFields(userProfile.getUserAddress(), userDto.getUserAddress()))
        	userProfile.setUserAddress(userDto.getUserAddress());

        if (!checkBothFields(userProfile.getPassword(), userDto.getPassword())) {
        	String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        	userProfile.setPassword(encodedPassword);
        }
        	
        
        UserProfile updatedUserProfile = userRepository.save(userProfile);
        
        return sendUserDTOResponse(updatedUserProfile);
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
	
	private Boolean checkBothFields(String s1, String s2) {
		if (s1.equals(s2))
				return true;
		return false;
	}
}

/*
{
  "aadharNumber": "574738145325",
  "userAddress": "Dakpathar, Dehradun",
  "phoneNumber": 6396474255,
  "emailAddress": "soapmactevis1@gmail.com",
  "fullName": "Vivek Kumar Ghosh",
  "userName": "itsvivekghosh"
  "password": "Vivek@1999"
}
 * */
