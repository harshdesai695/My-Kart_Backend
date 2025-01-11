package com.myKart.user.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Users")
public class User {
	@Id
	private String userId;
	private String userName;
	private String email;
	private String phoneNo;
	private String password;
	private String createdAt;
	private String imgUrl;
	
	public User() {
		
	}
	public User(String userId, String userName, String email,String phoneNo, String password, String createdAt) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.phoneNo=phoneNo;
		this.password = password;
		this.createdAt = createdAt;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", phoneNo=" + phoneNo
				+ ", createdAt=" + createdAt + ", imgUrl=" + imgUrl + "]";
	}

	
	
}
