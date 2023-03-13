package com.example.controller.shop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.Role;
import com.example.dto.Login;
import com.example.dto.Register;
import com.example.model.User;
import com.example.service.SecurityService;
import com.example.service.UserService;

@Controller
@RequestMapping(value = "molla/auth")
public class ShopAuthController {

	@Autowired
	UserService userService;

	@Autowired
	SecurityService securityService;

	@PostMapping(value = "/login")
	@ResponseBody
	public ResponseEntity<String> loginSubmit(Model model, @RequestBody Login login) {
		if (!userService.existsByUsername(login.getUsername()))
			return new ResponseEntity<String>("Tên đăng nhập không tồn tại", HttpStatus.INTERNAL_SERVER_ERROR);
		securityService.autoLogin(login.getUsername(), login.getPassword());
		return new ResponseEntity<String>("Chúc mừng bạn đã đăng nhập thành công.", HttpStatus.OK);
	}

	@PostMapping(value = "/register")
	@ResponseBody
	public ResponseEntity<String> registerSubmit(Model model, @RequestBody Register register) {
		if (userService.existsByUsername(register.getUsername()))
			return new ResponseEntity<String>("Tên đăng nhập đã được sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
		if (userService.existsByEmail(register.getEmail()))
			return new ResponseEntity<String>("Email đã được sử dụng", HttpStatus.INTERNAL_SERVER_ERROR);
		User user = new User();
		user.setUsername(register.getUsername());
		user.setEmail(register.getEmail());
		user.setPassword(register.getPassword());
		user.setLogin(true);
		user.setRole(Role.ROLE_USER);
		userService.saveOrUpdate(user);
		return new ResponseEntity<String>("Chúc mừng bạn đã đăng ký thành công.", HttpStatus.OK);
	}

	@GetMapping(value = "/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		securityService.logout(request, response);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
