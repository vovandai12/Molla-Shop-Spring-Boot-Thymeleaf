package com.example.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Category;
import com.example.service.CategoryService;

@Controller
@RequestMapping(value = "molla/layout")
public class ShopLayoutController {

	@Autowired
	CategoryService categoryService;

	@GetMapping(value = "/category")
	public ResponseEntity<List<Category>> categoryApi() {
		List<Category> list = categoryService.findAll();
		return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
	}
}
