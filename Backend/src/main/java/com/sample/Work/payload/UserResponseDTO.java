package com.sample.Work.payload;

import com.sample.Work.model.UserEntity;

public class UserResponseDTO {
	
	private String userName;
	
	private String name;
	
	public UserResponseDTO(String username, String name) {
		this.userName = username;
		this.name = name;
	}
	
	public UserResponseDTO(UserEntity user) {
		this.userName = user.getUserName();
		this.name = user.getName();
	}
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
