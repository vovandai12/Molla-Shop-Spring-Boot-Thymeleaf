package com.example.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.service.CustomUserDetailsService;
import com.example.service.SecurityService;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	private static Logger logger = LoggerFactory.getLogger(MyAccessDeniedHandler.class);
	
	@Autowired
	SecurityService securityService;

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException, ServletException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetailsService userDetails = (CustomUserDetailsService) auth.getPrincipal();
		System.out.println(userDetails.getPassword() + userDetails.getUsername()+ userDetails.getAuthorities());
		if (auth != null) {
			logger.info("User '" + auth.getName() + "' attempted to access the protected URL: "
					+ httpServletRequest.getRequestURI());
		}
		String uri = httpServletRequest.getRequestURI();
		if(uri.contains("shop")) {
			System.out.println(1);
		}else {
			securityService.logout(httpServletRequest, httpServletResponse);
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "error/admin/403");
		}

	}
}