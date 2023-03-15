package com.example.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "molla/check-out")
public class ShopCheckoutController {

	@GetMapping(value = "")
	public String checkOut() {
		return "shop/cart/check-out";
	}
}
