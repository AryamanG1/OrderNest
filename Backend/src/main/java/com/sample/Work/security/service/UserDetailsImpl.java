package com.sample.Work.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.Work.model.UserEntity;


public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID=1L;
	
	private Long id;
	private String userName;
	private String email;
	
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserDetailsImpl(Long uuid, String userName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = uuid;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	

	public UserDetailsImpl() {
	}

	public static UserDetailsImpl build(UserEntity user){
		List<GrantedAuthority> authorities = user.getRoles()
												 .stream()
												 .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
												 .collect(Collectors.toList());
		return new UserDetailsImpl(
				user.getUserId(),
				user.getUserName(),
				user.getEmail(),
				user.getPassword(),
				authorities);
				
	
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	@Override
	public String getUsername() {
		return userName;
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		if(this==o) {return true;}
		if(o==null || getClass()!=o.getClass()) {return false;}
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id,user.id);
	}

}
