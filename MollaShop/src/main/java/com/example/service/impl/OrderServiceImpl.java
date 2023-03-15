package com.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderRepository orderRepository;

	@Override
	public Optional<Order> saveOrUpdate(Order order) {
		Order orderOld = orderRepository.save(order);
		return Optional.of(orderOld);
	}

	@Override
	public Optional<Order> findById(Long id) {
		Optional<Order> order = orderRepository.findById(id);
		return order;
	}

	@Override
	public void deleteById(Long id) {
		orderRepository.deleteById(id);
	}
}
