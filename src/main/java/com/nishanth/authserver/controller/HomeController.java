package com.nishanth.authserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nishanth.authserver.entity.Contact;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
	
	List<Contact> contacts = new ArrayList<Contact>();
    
	Contact contact = new Contact(1,"Nishanth","7899888","shdh@gmail.com");
	
	@GetMapping("/home")
	public String getHome() {
		return "welcome to Authserver";
	}
	
//	Getting the csrf token and pass it in the headers of all opertions other than Get.
//	otherwise we can't do that operation.This is the feature provided by spring security to avoid csrf forgery
	@GetMapping("/csrf")
	public CsrfToken getCsrfToken(HttpServletRequest http) {
		return (CsrfToken) http.getAttribute("_csrf");        
	}                                                         

	@GetMapping("/sessionId")
	public String getSessionId(HttpServletRequest http) {
		return http.getSession().getId();
	}
	
	@GetMapping("/contact")
	public ArrayList<Contact> getContact() {
		System.out.println(contacts);
		return (ArrayList<Contact>) contacts ;
	}
	
	@PostMapping("/contact")
	public Contact addContact(@RequestBody Contact contact) {
		contacts.add(contact);
		return  contact ;
	}
}
