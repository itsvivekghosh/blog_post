package com.vivekghosh.springboottutorials.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_profile")
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userProfileId;
	
	@Column(unique = true, name = "user_name", nullable = false)
	private String userName;
	

	@Column(name = "full_name", nullable = false)
	private String fullName;
	
	@Column(unique = true, name="email_address", nullable = false)
	private String emailAddress;
	
	@Column(unique = true, name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@Column(name = "address", nullable = false)
	private String userAddress;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(unique = true, name = "aadhar_number", nullable = false)
	private String aadharNumber;
	
	@CreatedDate
    private Date activeSince = new Date();
	
	@Column(name = "last_profile_updated")
	private LocalDateTime lastProfileUpdated;

	@Column(name = "last_password_updated")
	private LocalDateTime lastPasswordUpdated;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "user_profile_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles;
	
}
