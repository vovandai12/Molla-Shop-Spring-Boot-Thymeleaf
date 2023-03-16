package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
	
	List<Like> findAllByUserId(String id);
	
}
