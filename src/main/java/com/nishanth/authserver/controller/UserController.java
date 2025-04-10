package com.nishanth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nishanth.authserver.entity.UserEntity;
import com.nishanth.authserver.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);   //it is used to Bcrypt the password and strength is 12 means it is hashing to 12 times
	
	@PostMapping("/register")
	public String addUser(@RequestBody UserEntity user) throws Exception {
		user.setPassword(encoder.encode(user.getPassword()));
		return userService.addUser(user);
		
	}
	
	@PostMapping("/login")
	public String userlogin(@RequestBody UserEntity user) throws Exception {
	try {
		return userService.verifyUser(user);
	}catch(Exception ex) {
		return "Login failed.Provide correct username and password";
	}
		
	}
}
