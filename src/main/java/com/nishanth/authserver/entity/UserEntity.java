package com.nishanth.authserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true,nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;
//	@Enumerated(EnumType.STRING)
//	private Role role; 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	} 
	public String getUserName() { 
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
