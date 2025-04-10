package com.nishanth.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nishanth.authserver.entity.UserEntity;
import com.nishanth.authserver.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private JwtService jwtService;
	
  
	@Autowired
	private AuthenticationManager authenticationManager;
	

	
	public String addUser(UserEntity user) throws Exception {
		    repo.save(user);
		    return "user registered";
		  
		
	}



	public String verifyUser(UserEntity user) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if(authentication.isAuthenticated()) {
		   return jwtService.generateToken(user.getUserName());
		}
		return "Login failed.Provide correct username and password";
		
	}



}
