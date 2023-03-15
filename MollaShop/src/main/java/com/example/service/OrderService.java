package com.example.service;

import java.util.Optional;

import com.example.model.Order;

public interface OrderService {

	Optional<Order> saveOrUpdate(Order order);
	
	Optional<Order> findById(Long id);
	
	void deleteById(Long id);
}
