package com.vivekghosh.springboottutorials.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vivekghosh.springboottutorials.entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
