package com.sample.Work.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.Work.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
	Optional<UserEntity> findByUserName(String userName);
	Boolean  existsByUserName(String userName);
	Boolean  existsByEmail(String email);
}
