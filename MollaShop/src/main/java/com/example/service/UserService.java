package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.User;

public interface UserService {
	Optional<User> saveOrUpdate(User user);
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findById(String id);
	
	void deleteById(String id);
	
	void updateLastLoginDate(User user);
	
	List<User> findAll();

	Boolean existsByUsername(String userName);

	Boolean existsByEmail(String email);
}