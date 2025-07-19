package com.sample.Work.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.Work.model.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

}
