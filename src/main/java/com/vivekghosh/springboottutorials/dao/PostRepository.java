package com.vivekghosh.springboottutorials.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivekghosh.springboottutorials.entities.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
