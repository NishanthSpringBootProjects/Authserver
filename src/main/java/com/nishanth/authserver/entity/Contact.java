package com.nishanth.authserver.entity;

public class Contact {
	
	public Contact(int id, String name, String phoneNum, String emailId) {
		super();
		this.id = id;
		this.name = name;
		this.phoneNum = phoneNum;
		this.emailId = emailId;
	}
	private int id;
	private String name;
	private String phoneNum;
	private String emailId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() { 
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", phoneNum=" + phoneNum + ", emailId=" + emailId + "]";
	}
	

}
