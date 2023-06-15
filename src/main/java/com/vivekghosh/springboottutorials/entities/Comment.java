package com.vivekghosh.springboottutorials.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;
	
	@NotBlank
	@Column(name = "comment_body")
	@Size(min = 10, message = "Comment body must be minimum 10 characters")
	private String commentBody;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;
    
}
