package com.sathish.springsecuritydemo.springsecuriy.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sathish.springsecuritydemo.springsecuriy.dto.LoginDto;
import com.sathish.springsecuritydemo.springsecuriy.dto.RegisterDto;
import com.sathish.springsecuritydemo.springsecuriy.entity.Role;
import com.sathish.springsecuritydemo.springsecuriy.entity.User;
import com.sathish.springsecuritydemo.springsecuriy.repository.RoleRepository;
import com.sathish.springsecuritydemo.springsecuriy.repository.UserRepository;
import com.sathish.springsecuritydemo.springsecuriy.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {
	private AuthenticationManager autheticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	


	public AuthServiceImpl(AuthenticationManager autheticationManager, 
													UserRepository userRepository,
													RoleRepository roleRepository, 
													PasswordEncoder passwordEncoder,
													JwtTokenProvider jwtTokenProvider) {
		super();
		this.autheticationManager = autheticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {
		
		// get Authentication object from AuthenticationManager 
		Authentication authentication = autheticationManager.authenticate(new UsernamePasswordAuthenticationToken( 
				loginDto.getUsernameOrEmail(),loginDto.getPassword())
		);
		
		// store authentication in spring context holder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		
		// check if user already exists by username
		if(userRepository.existsByUsername(registerDto.getUsername())) {
//			throw new Exception("Username already exists");
			return "Username Already Exists";
		}
		//
		if(userRepository.existsByEmail(registerDto.getEmail())) {
//			throw new Exception("Email already exists");
			return "Email Already Exists";
		}
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		userRepository.save(user);
		return "User Registered Successfully..!";
		
	}

}
