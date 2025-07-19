package com.sample.Work.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
@Table(name = "token")
public class TokenEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotBlank(message = "Token cannot be empty")
	private String token;
	
	private boolean loggedOut;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	


	public TokenEntity( @NotBlank(message = "Token cannot be empty") String token, boolean loggedOut,
			UserEntity user) {
		this.token = token;
		this.loggedOut = loggedOut;
		this.user = user;
	}
	
	public TokenEntity() {
	}

	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public boolean isLoggedOut() {
		return loggedOut;
	}


	public void setLoggedOut(boolean loggedOut) {
		this.loggedOut = loggedOut;
	}


	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	
	
	

}
