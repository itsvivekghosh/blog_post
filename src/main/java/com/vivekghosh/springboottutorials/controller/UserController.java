/**
 * 
 */
package com.vivekghosh.springboottutorials.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivekghosh.springboottutorials.Payloads.GenericPayload;
import com.vivekghosh.springboottutorials.Services.UserProfile.UserService;
import com.vivekghosh.springboottutorials.dto.PasswordDTO;
import com.vivekghosh.springboottutorials.dto.UserDTO;


@RestController
@RequestMapping("/api/v1/user_profile")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/create_", consumes={ "application/json" })
	public ResponseEntity<UserDTO> createUserProfile(@Valid @RequestBody UserDTO userDto) {
			
		return new ResponseEntity<UserDTO>(
			userService.createUserProfile(userDto), HttpStatus.CREATED
		);
	}
	
	@GetMapping(path="/fetch_all_")
	public ResponseEntity<List<UserDTO>> getUserProfiles() {
		
		return new ResponseEntity<List<UserDTO>>(
			userService.getAllUserProfiles(), HttpStatus.OK
		);
	}
	
	@GetMapping(path="/{id}")
	public ResponseEntity<UserDTO> getUserProfileById(@PathVariable(name = "id") Long id) {
		
		return new ResponseEntity<UserDTO>(
			userService.getProfileById(id), HttpStatus.OK
		);
	}
	
	@GetMapping(path="/search/user_name/")
	public ResponseEntity<UserDTO> getUserProfileByUserName(@RequestParam(value = "user_name") String user_name) {
		
		return new ResponseEntity<UserDTO>(
			userService.getProfileByUserName(user_name), HttpStatus.OK
		);
	}
	
	@GetMapping(path="/search/aadhar_number/")
	public ResponseEntity<UserDTO> getUserProfileByAadharNumber(@RequestParam(value = "aadhar_number") String aadhar_number) {
		
		return new ResponseEntity<UserDTO>(
			userService.getProfileByAadharNumber(aadhar_number), HttpStatus.OK
		);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUserProfileByUserAadharNumber(@RequestParam(value = "keyword") String keyword) {
		
		return new ResponseEntity<List<UserDTO>>(
			userService.getProfileBySearchedKeyword(keyword), HttpStatus.OK
		);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable(name = "id") Long id, @RequestBody UserDTO userDto){

       return new ResponseEntity<UserDTO>(
    		   userService.updateUserProfile(userDto, id), HttpStatus.OK
	   );
    }
	
	@PutMapping("/{id}/update_password_")
    public ResponseEntity<GenericPayload> updateUserPassword(@PathVariable(name = "id") Long id, @RequestBody PasswordDTO passwordDTO){

       return new ResponseEntity<GenericPayload>(
    		   userService.updateUserPassword(passwordDTO, id), HttpStatus.OK
	   );
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUserProfile(@PathVariable(name = "id") Long id){

       return new ResponseEntity<UserDTO>(
    		   userService.deleteUserProfile(id), HttpStatus.OK
	   );
    }
}
