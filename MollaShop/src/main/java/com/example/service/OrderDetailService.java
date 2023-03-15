package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.OrderDetail;

public interface OrderDetailService {

	Optional<OrderDetail> saveOrUpdate(OrderDetail orderDetail);

	List<OrderDetail> findAllByOrderId(Long id);
}
