package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findAllByNameLike(String keyword, Pageable pageable);

	Page<Product> findAllByNameLikeAndColorLike(String keyword, String color, Pageable pageable);

	Page<Product> findAllByNameLikeAndCategoryId(String keyword, Long id, Pageable pageable);

	Page<Product> findAllByNameLikeAndBrandId(String keyword, Long id, Pageable pageable);

}
