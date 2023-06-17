/**
 * 
 */
package com.vivekghosh.springboottutorials.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vivekghosh.springboottutorials.entities.UserProfile;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {
	
	public Optional<UserProfile> findByUserName(String user_name);
	
	public Optional<UserProfile> findByAadharNumber(String adhaar_number);
	
	public Optional<UserProfile> findByEmailAddress(String email);
	
	public Optional<UserProfile> findByUserNameOrEmailAddress(String userName, String email);


	@Query("SELECT u FROM UserProfile u WHERE u.fullName LIKE %?1%"
            + " OR u.userName LIKE %?1%"
            + " OR u.aadharNumber LIKE %?1%"
            + " OR u.emailAddress LIKE %?1%"
            + " OR CONCAT(u.phoneNumber, '') LIKE %?1%")
	public List<UserProfile> getProfileBySearchedKeyword(String keyword);
	
	Boolean existsByUserName(String username);

    Boolean existsByEmailAddress(String email);
}
