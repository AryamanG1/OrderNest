package com.sample.Work.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.Work.model.OrderProductEntity;
import com.sample.Work.model.OrderProductId;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity,OrderProductId>{
	
}
