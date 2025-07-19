package com.sample.Work.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.Work.model.OrderEntity;
import com.sample.Work.model.UserEntity;
import com.sample.Work.model.UserOrderEntity;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity,Long>{
	List<UserOrderEntity> findByUser(UserEntity User);
}
