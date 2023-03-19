package com.example.controller.admin;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.example.common.MailType;
import com.example.common.Role;
import com.example.dto.Login;
import com.example.dto.MailInfo;
import com.example.dto.Register;
import com.example.model.User;
import com.example.service.MailerService;
import com.example.service.SecurityService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@Autowired
	MailerService mailerService;

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

	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		securityService.logout(request, response);
		return "redirect:/auth/login";
	}

	@GetMapping(value = "/forgot-password")
	public String forgotPassword() {
		return "auth/admin/forgot-password";
	}

	@PostMapping(value = "/forgot-password/submit")
	public String forgotPasswordSubmit(Model model, @RequestParam(name = "email") String email) {
		if (!userService.existsByEmail(email)) {
			model.addAttribute("error", "Email không tồn tại tài khoản nào");
			return "auth/admin/forgot-password";
		}

		MailInfo mailInfo = new MailInfo(email, "Quên mật khẩu", "", MailType.FORGOT);
		try {
			mailerService.send(mailInfo);
		} catch (MessagingException e) {
			e.printStackTrace();
			model.addAttribute("error", "Gửi emai không thành công");
			return "auth/admin/forgot-password";
		}
		model.addAttribute("message", "Một thông báo quên mật khẩu đã gửi tới email của bạn");
		return "auth/admin/forgot-password";
	}
}
