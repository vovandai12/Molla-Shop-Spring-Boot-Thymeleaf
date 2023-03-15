package com.example.controller.shop;

import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.Item;
import com.example.dto.OrderAddressDto;
import com.example.model.OrderAddress;
import com.example.model.User;
import com.example.service.CartService;
import com.example.service.OrderAddressService;
import com.example.service.SecurityService;
import com.example.service.SessionService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "molla/check-out")
public class ShopCheckoutController {

	@Autowired
	CartService cartService;

	@Autowired
	SessionService session;

	@Autowired
	SecurityService securityService;

	@Autowired
	UserService userService;

	@Autowired
	OrderAddressService orderAddressService;

	@GetMapping(value = "")
	public String checkOut(Model model) {
		Collection<Item> items = cartService.getItems();
		model.addAttribute("carts", items);

		float totail = cartService.getTotail();
		if (session.get("shipping").toString().equals("1"))
			totail = totail + 10000;
		else if (session.get("shipping").toString().equals("2"))
			totail = totail + 20000;
		model.addAttribute("totalCart", totail);

		model.addAttribute("shipping", session.get("shipping").toString());

		OrderAddressDto orderAddressDto = new OrderAddressDto();
		if (securityService.isAuthenticated()) {
			String username = securityService.username();
			User user = userService.findByUsername(username).get();
			if (orderAddressService.existsByEmail(user.getEmail())) {
				OrderAddress orderAddress = orderAddressService.findByEmail(user.getEmail()).get();
				orderAddressDto.setId(orderAddress.getId());
				orderAddressDto.setFirstName(orderAddress.getFirstName());
				orderAddressDto.setLastName(orderAddress.getLastName());
				orderAddressDto.setAddress(orderAddress.getAddress());
				orderAddressDto.setPhone(orderAddress.getPhone());
				orderAddressDto.setEmail(orderAddress.getEmail());
			} else {
				orderAddressDto.setFirstName(user.getFirstName());
				orderAddressDto.setLastName(user.getLastName());
				orderAddressDto.setAddress(user.getAddress());
				orderAddressDto.setEmail(user.getEmail());
			}
		}
		model.addAttribute("orderAddressDto", orderAddressDto);
		return "shop/cart/check-out";
	}

	@PostMapping(value = "/submit")
	public String checkOutSubmit(Model model, @Valid @ModelAttribute("userDto") OrderAddressDto orderAddressDto,
			BindingResult result, @RequestParam(name = "note") String note, @RequestParam(name = "pay") String pay)
			throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "shop/cart/check-out";
		}
		System.out.println(note);
		System.out.println(pay);
		model.addAttribute("message",
				"Đã đặt hàng thành công, truy cập lịch sử đơn hàng hoặc tra cứu đơn hàng để biết thêm thông tin.");
		return "redirect:/molla/home";
	}
}
