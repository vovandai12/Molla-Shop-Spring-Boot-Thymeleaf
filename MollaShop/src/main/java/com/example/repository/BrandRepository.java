package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	
	Page<Brand> findAllByNameLike(String keyword, Pageable pageable);

}