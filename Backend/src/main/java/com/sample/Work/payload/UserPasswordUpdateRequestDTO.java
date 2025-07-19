package com.sample.Work.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserPasswordUpdateRequestDTO {

	@NotBlank(message = "Password cannot be empty")
	@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
	@Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
	    message = "Password must include uppercase, lowercase, number, and special character"
	)
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserPasswordUpdateRequestDTO(
			@NotBlank(message = "Password cannot be empty") @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$", message = "Password must include uppercase, lowercase, number, and special character") String password) {
		this.password = password;
	}

	public UserPasswordUpdateRequestDTO() {
	}
	
	
	
	
}
