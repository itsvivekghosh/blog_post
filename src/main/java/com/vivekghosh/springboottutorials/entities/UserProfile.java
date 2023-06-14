package com.vivekghosh.springboottutorials.entities;

import java.util.Set;

import javax.persistence.*;

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
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_profile_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles;
	
}
