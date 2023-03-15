package com.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.OrderAddress;
import com.example.repository.OrderAddressRepository;
import com.example.service.OrderAddressService;

@Service
public class OrderAddressServiceImpl implements OrderAddressService {

	@Autowired
	OrderAddressRepository orderAddressRepository;

	@Override
	public Optional<OrderAddress> saveOrUpdate(OrderAddress orderAddress) {
		OrderAddress orderAddressOld = orderAddressRepository.save(orderAddress);
		return Optional.of(orderAddressOld);
	}

	@Override
	public Optional<OrderAddress> findById(Long id) {
		return orderAddressRepository.findById(id);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return orderAddressRepository.existsByEmail(email);
	}

	@Override
	public Optional<OrderAddress> findByEmail(String email) {
		return orderAddressRepository.findByEmail(email);
	}

}
