package com.vivekghosh.springboottutorials.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivekghosh.springboottutorials.entities.LoginLog;


@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {

}
