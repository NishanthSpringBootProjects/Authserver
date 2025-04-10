package com.nishanth.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nishanth.authserver.entity.UserEntity;
import com.nishanth.authserver.repo.UserRepo;

@Service 
public class UserDetailsService  implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	/*
	 * UserDetails interface is used to represent an authenticated user.We create a
	 * UserPrinciple (or CustomUserDetails) class that implements UserDetails and
	 * loads user information from the database.
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = repo.findByUserName(username);
		if(user == null || ! user.getUserName().equals(username)) {
			System.out.println("User not Found");
			throw new UsernameNotFoundException("user not found");
		}else {
		return new UserPrinciple(user);
		}
	}

}
