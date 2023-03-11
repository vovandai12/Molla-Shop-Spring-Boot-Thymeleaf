package com.example.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.Product;

public interface ProductService {

	Optional<Product> saveOrUpdate(Product product);

	Optional<Product> findById(Long id);

	void deleteById(Long id);

	Page<Product> findAllByNameLike(String keyword, Pageable pageable);
}
