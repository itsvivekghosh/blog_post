/**
 * 
 */
package com.vivekghosh.springboottutorials.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivekghosh.springboottutorials.Payloads.JWTAuthResponse;
import com.vivekghosh.springboottutorials.Services.Authentication.AuthenticationService;
import com.vivekghosh.springboottutorials.Services.UserProfile.UserService;
import com.vivekghosh.springboottutorials.dto.LoginDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationService authService;
	
	 public AuthController(AuthenticationService authService) {
        this.authService = authService;
	 }

	// Build Login REST API
    @PostMapping(value = {"/login"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDto) {
    	
        String authToken = authService.userLogin(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(authToken);
        
        if (authToken != null) {
        	return new ResponseEntity<JWTAuthResponse>(
    			jwtAuthResponse,
    			HttpStatus.OK
        	);
        }
        return new ResponseEntity<JWTAuthResponse>(
			jwtAuthResponse,
			HttpStatus.UNAUTHORIZED
    	);
    }
    
    // Build Register REST API
    @PostMapping(value = {"/register"})
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDto) {
    	
        UserDTO response = authService.userRegister(userDto);
        
        return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
        
    }

}
