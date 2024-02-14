package com.sathish.springsecuritydemo.springsecuriy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sathish.springsecuritydemo.springsecuriy.dto.JwtAuthResponse;
import com.sathish.springsecuritydemo.springsecuriy.dto.LoginDto;
import com.sathish.springsecuritydemo.springsecuriy.dto.RegisterDto;
import com.sathish.springsecuritydemo.springsecuriy.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	//Login Rest Api
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authService.login(loginDto);
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setAccessToken(token);
		return ResponseEntity.ok(authResponse);
	}
	
	//Build Register rest api
	@PostMapping(value= {"/register","signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
;