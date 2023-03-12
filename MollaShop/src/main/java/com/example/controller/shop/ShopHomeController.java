package com.example.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "molla")
public class ShopHomeController {

	@GetMapping(value = "/home")
	public String home() {
		return "shop/home/index";
	}
}
