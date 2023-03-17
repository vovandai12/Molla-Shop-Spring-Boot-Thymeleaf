package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findAllByOrderAddressEmailOrderByCreatedDateAsc(String email);
	
	Page<Order> findAllByOrderAddressEmailLike(String keyword, Pageable pageable);
	
	@Query(value = "SELECT YEAR(o.created_date) AS year "
			+ "FROM orders o "
			+ "GROUP BY YEAR(o.created_date) "
			+ "ORDER BY YEAR(o.created_date) ASC", nativeQuery = true)
	List<Integer> getYearOrder();
	
}
