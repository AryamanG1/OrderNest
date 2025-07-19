package com.sample.Work.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sample.Work.model.UserEntity;
import com.sample.Work.payload.*;
import com.sample.Work.response.ApiResponse;
import com.sample.Work.service.OrderServiceImpl;
import com.sample.Work.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "User Controller", description = "Handles User management functionalities")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PutMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserDetails(@RequestBody @Valid UserUpdateRequestDTO user){
		try {
			UserResponseDTO newUser = userService.updateUser(user);
			return ResponseEntity.ok(ApiResponse.success("User Details Successfully updated", newUser));
		}
		catch(ResponseStatusException e) {
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()), e.getStatusCode());
		}
	}
	
	@PutMapping("/user/pass")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserPassword(@RequestBody @Valid UserPasswordUpdateRequestDTO password){
		try {
			UserResponseDTO updatedUser= userService.updatePassword(password);
			return ResponseEntity.ok(ApiResponse.success("Successfully updated password", updatedUser));
			
		}
		catch(ResponseStatusException e){
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','SELLER','ADMIN')")
	public ResponseEntity<ApiResponse<UserEntity>> getCurrentUserDetails(){
		try {
			UserEntity user = userService.viewUserDetails();
			return ResponseEntity.ok(ApiResponse.success("Details of User Successfully found", user));
		}
		catch(ResponseStatusException e) {
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	@DeleteMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ApiResponse<UserResponseDTO>> deleteAccountOfUser(){
		try {
			UserResponseDTO deletedUser = userService.deleteUserSelf();
			return ResponseEntity.ok(ApiResponse.success("Successfully deleted user", deletedUser));
		}
		catch(ResponseStatusException e) {
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	
	@GetMapping("/admin/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUser(){
		try {
			List<UserResponseDTO> allUser = userService.viewUserAdmin();
			return ResponseEntity.ok(ApiResponse.success("Successfully found all users ", allUser));
		}
		catch(ResponseStatusException e) {
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	@DeleteMapping("/admin/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<?>> deleteUserAdmin(@RequestBody @Valid UserDeleteRequestDTO request){
		try {
			UserResponseDTO deletedUser = userService.deleteUserAdmin(request);
			return ResponseEntity.ok(ApiResponse.success("Successfully deleted user", deletedUser));
		}
		catch(ResponseStatusException e) {
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	

}
