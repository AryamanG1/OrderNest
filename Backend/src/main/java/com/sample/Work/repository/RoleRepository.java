package com.sample.Work.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.Work.model.AppRole;
import com.sample.Work.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
	Optional<Role> findByRoleName(AppRole appRole);
}
