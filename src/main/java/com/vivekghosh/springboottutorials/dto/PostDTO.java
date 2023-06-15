package com.vivekghosh.springboottutorials.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.vivekghosh.springboottutorials.entities.UserProfile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(
        description = "PostDTO Model Information"
)
public class PostDTO {

	private Long id;

    @Schema(
            description = "Blog Post Title"
    )
    // title should not be null  or empty
    // title should have at least 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String postTitle;

    @Schema(
            description = "Blog Post Description"
    )
    // post description should be not null or empty
    // post description should have at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String postDescription;

    @Schema(
            description = "Blog Post Content"
    )
    // post content should not be null or empty
    @NotEmpty
    private String postContent;
    
    @Schema(description = "No. of likes in the post")
    private Long postLikes;
    
    @Schema(description = "is the post updated")
    private Boolean isUpdated;
    
    @Schema(description = "Post Created Date Time")
    private Date postCreatedAt = new Date();
    
    @Schema(description = "Post Last Update Time")
    private LocalDateTime postUpdatedAt;
    
    @Schema(description = "User ID of the Blog Post")
    private Long userId;

    @Schema(description = "User Name of the Blog Post")
    private String userName;
}
