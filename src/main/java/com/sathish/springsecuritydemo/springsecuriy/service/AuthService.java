package com.sathish.springsecuritydemo.springsecuriy.service;

import com.sathish.springsecuritydemo.springsecuriy.dto.LoginDto;
import com.sathish.springsecuritydemo.springsecuriy.dto.RegisterDto;

public interface AuthService {
	public String login(LoginDto loginDto);
	public String register(RegisterDto registerDto);
}
