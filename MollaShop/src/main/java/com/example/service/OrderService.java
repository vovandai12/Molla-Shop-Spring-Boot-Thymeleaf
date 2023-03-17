package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.Order;

public interface OrderService {

	Optional<Order> saveOrUpdate(Order order);
	
	Optional<Order> findById(Long id);
	
	void deleteById(Long id);
	
	List<Order> findAllByOrderAddressEmail(String email);
	
	Page<Order> findAllByOrderAddressEmailLike(String keyword, Pageable pageable);
	
	List<Integer> getYearOrder();
}
