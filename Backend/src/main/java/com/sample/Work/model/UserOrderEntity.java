package com.sample.Work.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "user_order")
public class UserOrderEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private UserEntity user;
	
	@OneToOne
	@JoinColumn(name="order_id",nullable=false)
	@JsonBackReference
	private OrderEntity order;

	public UserOrderEntity(UserEntity user, OrderEntity order) {
		this.user = user;
		this.order = order;
	}

	

	public UserOrderEntity() {
	}



	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}
	
	
	
}
