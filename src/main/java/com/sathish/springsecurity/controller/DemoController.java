package com.sathish.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
	
	@PreAuthorize("hasRole('ADMIN') or  hasRole('MODERATOR')  or  hasRole('USER')")
	@GetMapping("/home")
	public String home() {
		return "Home Page........";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		return "Admin Page.........";
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/users")
	public String users() {
		return "Users page........";
	}
}
