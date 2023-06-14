package com.vivekghosh.springboottutorials.dto;

import javax.validation.constraints.*;

import lombok.Data;


@Data
public class UserDTO {
	
	private Long userProfileId;
	
    @NotEmpty
    @Size(min = 6, max = 30, message = "User Name should have at least 6 characters")
	private String userName;
    
    @NotEmpty
    @Size(min = 5, message = "User Full Name should have at least 7 characters")
	private String fullName;
	
	@Email(message = "Invalid Email-Id. Please enter proper Email Id")
    @NotEmpty(message = "Please enter your Email Id")
	private String emailAddress;
	
	@NotEmpty(message = "Please enter your Phone Number")
    @Size(min = 10, max = 10, message = "Phone number should have exactly 10 characters")
	private String phoneNumber;
	
	@NotEmpty
	@Size(min = 10, message = "Address should have at least 10 characters")
	private String userAddress;

	@NotEmpty
	@Size(min = 10, max = 20, message = "Password should have at least 10 characters and max 10 characters")
	private String password;
	
	@NotEmpty
	@Size(min = 12, max = 12, message = "Aadhar Number should have exaclty 16 characters")
	private String aadharNumber;
	
}
