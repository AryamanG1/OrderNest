package com.sample.Work.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="users",
uniqueConstraints= {@UniqueConstraint(columnNames = {"user_id"}),
					@UniqueConstraint(columnNames = {"user_name"}),
					@UniqueConstraint(columnNames = {"email"})}
)
public class UserEntity {
	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Long userId;
	
	@NotBlank(message = "Username  cannot be left blank")
	@Column(name="user_name")
	private String userName;
	
	@NotBlank(message = "Name cannot be left blank")
	private String name;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email field cannot be left blank")
	private String email;
	
	@Column(nullable = false)
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
    
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval=true , fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserOrderEntity> userOrders = new HashSet<>();
    
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(
    		name="user_role",
    		joinColumns = @JoinColumn(name="user_id"),
    		inverseJoinColumns = @JoinColumn(name="role_id")
    		)
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy="user")
    private List<TokenEntity> tokens = new ArrayList<>();
 
	
	
	

	public UserEntity(@NotBlank(message = "Username  cannot be left blank") String userName,
			@NotBlank(message = "Name cannot be left blank") String name,
			@Email(message = "Invalid email format") @NotBlank(message = "Email field cannot be left blank") String email,
			@NotBlank(message = "Password cannot be empty") @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$", message = "Password must include uppercase, lowercase, number, and special character") String password,
			@Min(0) @Max(100) int age) {
		super();
		this.userName = userName;
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
	}



	public UserEntity() {
	}



	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Set<UserOrderEntity> getUserOrders() {
		return userOrders;
	}

	public void setUserOrders(Set<UserOrderEntity> userOrders) {
		this.userOrders = userOrders;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
    
    
}
