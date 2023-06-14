package com.vivekghosh.springboottutorials.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vivekghosh.springboottutorials.dao.UserRepository;
import com.vivekghosh.springboottutorials.entities.UserProfile;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
          UserProfile user = userRepository.findByUserNameOrEmailAddress(usernameOrEmail, usernameOrEmail)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        Set<GrantedAuthority> authorities = user
            .getRoles()
            .stream()
            .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet()
        );

        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(),
            user.getPassword(),
            authorities
        );
    }
}
