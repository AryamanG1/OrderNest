package com.sample.Work.security.request;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupRequest {
	@NotBlank(message = "Username  cannot be left blank")
	private String username;
	
	@NotBlank(message = "Name cannot be left blank")
	private String name;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email field cannot be left blank")
	private String email;
	
	@NotBlank(message = "Password cannot be empty")
	@Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
	@Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
	    message = "Password must include uppercase, lowercase, number, and special character"
	)
	private String password;
	
	@Min(0)
	@Max(100)
	private int age;
    
    private Set<String> role;

	public SignupRequest(@NotBlank(message = "Username  cannot be left blank") String username,
			@NotBlank(message = "Name cannot be left blank") String name,
			@Email(message = "Invalid email format") @NotBlank(message = "Email field cannot be left blank") String email,
			@NotBlank(message = "Password cannot be empty") @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$", message = "Password must include uppercase, lowercase, number, and special character") String password,
			@Min(0) @Max(100) int age, Set<String> role) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}
    
    
	
    
}
