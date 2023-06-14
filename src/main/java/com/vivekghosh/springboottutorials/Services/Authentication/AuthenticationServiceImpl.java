package com.vivekghosh.springboottutorials.Services.Authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.vivekghosh.springboottutorials.Exceptions.BlogAPIException;
import com.vivekghosh.springboottutorials.Exceptions.ResourceNotFoundException;
import com.vivekghosh.springboottutorials.Security.JwtTokenProvider;
import com.vivekghosh.springboottutorials.Services.Helpers.MappingEntities;
import com.vivekghosh.springboottutorials.dao.LoginLogRepository;
import com.vivekghosh.springboottutorials.dao.RoleRepository;
import com.vivekghosh.springboottutorials.dao.UserRepository;
import com.vivekghosh.springboottutorials.dto.LoginDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;
import com.vivekghosh.springboottutorials.entities.LoginLog;
import com.vivekghosh.springboottutorials.entities.Role;
import com.vivekghosh.springboottutorials.entities.UserProfile;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private LoginLogRepository loginLogRepository;
	
	@Autowired
    private RoleRepository roleRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private JwtTokenProvider jwtTokenProvider;

    private MappingEntities mappingEntities;
	
	public AuthenticationServiceImpl() {
		mappingEntities = new MappingEntities();
	}
	
	
	@Override
	public String userLogin(LoginDTO loginDto) {
		
		String token = null;
		
		try {
			
			String searchParam = loginDto.getUsernameOrEmail();
			
			UserProfile user = userRepository.findByUserNameOrEmailAddress(searchParam, searchParam)
				.orElseThrow(() -> new ResourceNotFoundException("UserProfile", searchParam)
			);
			
			if (user != null) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
							loginDto.getUsernameOrEmail(), loginDto.getPassword()
						)
				);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				token = jwtTokenProvider.generateToken(authentication);
				
				if (token != null) {
					LoginLog loginLog = new LoginLog();
					loginLog.setUser(user);
					loginLogRepository.save(loginLog);
				}
				return token;
			} 
		} catch (Exception e) {
			System.err.println("Exception occurred: " + e.getMessage());
		}
		
		return token;
	}

	/*
	 {
	  "aadharNumber": "574738145325",
	  "emailAddress": "email@email.com",
	  "fullName": "Vivek Ghosh",
	  "password": "Vivek@1999",
	  "phoneNumber": "6396474255",
	  "userAddress": "Dakpathar, Dehradun",
	  "userName": "itsvivekghosh"
	}
	 */
	@Override
	public UserDTO userRegister(UserDTO userDto) {
		
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
			userResponse = mappingEntities.mapToDTO(newUser);
			
		} catch (Exception e) {
			System.err.println(e);
		}
        return userResponse;
	}

}
