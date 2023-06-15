package com.vivekghosh.springboottutorials.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(
        name = "posts"
//        ,uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_description", nullable = false)
    private String postDescription;

    @Column(name = "post_content", nullable = false)
    private String postContent;
    
    @Column(name = "post_likes", nullable = false, columnDefinition = "integer default 0")
    private Long postLikes;
    
    @Column(name = "is_updated", columnDefinition = "integer default false")
    private Boolean isUpdated;

    @CreationTimestamp
    @Column(name = "post_created_time")
    private Date postCreatedAt = new Date();
    
    @Column(name = "post_updated_time", nullable = true)
    private LocalDateTime postUpdatedAt;
    
//    @NotNull
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
//	private UserProfile userProfile;
    
    
    @NotNull
    @ManyToOne
    @JoinTable(name = "user_posts",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id")
            ,inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private UserProfile userProfile;
    
    
    public Post() {
    	this.postLikes = 0l;
    	this.isUpdated = false;
    }
    
}

