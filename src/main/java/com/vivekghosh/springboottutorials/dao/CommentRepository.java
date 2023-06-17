package com.vivekghosh.springboottutorials.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vivekghosh.springboottutorials.entities.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPostPostId(Long postId);
}
