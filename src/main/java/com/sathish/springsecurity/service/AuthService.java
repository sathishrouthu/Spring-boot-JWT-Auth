package com.sathish.springsecurity.service;

import com.sathish.springsecurity.dto.LoginDto;
import com.sathish.springsecurity.dto.RegisterDto;

public interface AuthService {
	public String login(LoginDto loginDto);
	public String register(RegisterDto registerDto);
}
