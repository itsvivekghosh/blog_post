package com.vivekghosh.springboottutorials.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
	
    private Long id;
    private String name;
    private String description;
    
}