package com.sample.Work.service;

import java.util.List;

import com.sample.Work.model.UserEntity;
import com.sample.Work.payload.UserDeleteRequestDTO;
import com.sample.Work.payload.UserPasswordUpdateRequestDTO;
import com.sample.Work.payload.UserResponseDTO;
import com.sample.Work.payload.UserUpdateRequestDTO;

public interface UserService {
	public UserResponseDTO updateUser(UserUpdateRequestDTO request);
	public UserResponseDTO updatePassword(UserPasswordUpdateRequestDTO request);
	public List<UserResponseDTO> viewUserAdmin();
	public UserEntity viewUserDetails();
	public UserResponseDTO deleteUserAdmin(UserDeleteRequestDTO request);
	public UserResponseDTO deleteUserSelf();
}
