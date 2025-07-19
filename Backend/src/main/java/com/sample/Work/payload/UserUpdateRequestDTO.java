package com.sample.Work.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class UserUpdateRequestDTO {
	
	
	private String name;
	
	@Email(message = "Invalid email format")
	private String email;
	
	@Min(value=8,message="Age must be at least 8 years")
	@Max(value=100,message="Age cannot be more than 100 years")
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public UserUpdateRequestDTO(String name, @Email(message = "Invalid email format") String email,
			@Min(value = 8, message = "Age must be at least 8 years") @Max(value = 100, message = "Age cannot be more than 100 years") Integer age) {
		this.name = name;
		this.email = email;
		this.age = age;
	}

	public UserUpdateRequestDTO() {
	}

	
	
	
	
	
	
	
}
