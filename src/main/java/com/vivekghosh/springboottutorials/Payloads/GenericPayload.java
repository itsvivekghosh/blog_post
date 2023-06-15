package com.vivekghosh.springboottutorials.Payloads;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericPayload {
	
    private Date timestamp = new Date();
    private String message;
    private HttpStatus status;
    
    public GenericPayload(String message, HttpStatus status) {
    	
    	this.timestamp = new Date();
    	this.message = message;
    	this.status = status;
    	
    }
}