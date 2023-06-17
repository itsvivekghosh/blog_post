package com.vivekghosh.springboottutorials.entities;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;

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
	@Size(min = 1, message = "Comment body must be minimum 1 characters")
	private String commentBody;
	
	@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "comment_created_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date commentCreatedDate = new Date();
    
}
