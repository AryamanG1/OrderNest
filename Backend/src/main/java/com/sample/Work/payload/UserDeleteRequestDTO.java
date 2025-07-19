package com.sample.Work.payload;


import jakarta.validation.constraints.NotBlank;

public class UserDeleteRequestDTO {
	
	@NotBlank(message = "Username  cannot be left blank")
	private String userName;

	public UserDeleteRequestDTO(@NotBlank(message = "Username  cannot be left blank") String userName) {
		this.userName = userName;
	}

	public UserDeleteRequestDTO() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

	
	
}
