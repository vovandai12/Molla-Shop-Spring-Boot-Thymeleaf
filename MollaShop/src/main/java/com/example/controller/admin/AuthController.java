package com.example.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.Role;
import com.example.dto.Login;
import com.example.dto.Register;
import com.example.model.User;
import com.example.service.SecurityService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@GetMapping(value = "/login")
	public String login(Login login) {
		if (securityService.isAuthenticated()) {
            return "redirect:/home";
        }
		return "auth/admin/login";
	}

	@PostMapping(value = "/login/submit")
	public String loginSubmit(Model model, @Valid @ModelAttribute(name = "login") Login login, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "auth/admin/login";
		}
		securityService.autoLogin(login.getUsername(), login.getPassword());
		return "redirect:/home";
	}

	@GetMapping(value = "/register")
	public String register(Register register) {
		if (securityService.isAuthenticated()) {
            return "redirect:/home";
        }
		return "auth/admin/register";
	}

	@PostMapping(value = "/register/submit")
	public String registerSubmit(Model model, @Valid @ModelAttribute(name = "register") Register register,
			BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("error", "Lỗi định dạng");
			return "auth/admin/register";
		}
		if (userService.existsByUsername(register.getUsername())) {
			model.addAttribute("error", "Tên đăng nhập đã được sử dụng");
			return "auth/admin/register";
		}
		if (userService.existsByEmail(register.getEmail())) {
			model.addAttribute("error", "Email đã được sử dụng");
			return "auth/admin/register";
		}
		User user = new User();
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		user.setPassword(register.getPassword());
		user.setLogin(true);
		user.setRole(Role.ROLE_ADMIN);
		userService.saveOrUpdate(user);
		return "redirect:/auth/login";
	}
}
