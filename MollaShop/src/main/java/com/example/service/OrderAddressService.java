package com.example.service;

import java.util.Optional;

import com.example.model.OrderAddress;

public interface OrderAddressService {
	Optional<OrderAddress> saveOrUpdate(OrderAddress orderAddress);
	
	Optional<OrderAddress> findById(Long orderAddress);
	
	Boolean existsByEmail(String email);
	
	Optional<OrderAddress> findByEmail(String email);
}