package com.example.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "molla")
public class ShopAboutController {

	@GetMapping(value = "/about")
	public String about() {
		return "shop/about/about";
	}
}
