package com.sample.Work.model;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "orders")
public class OrderEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Column(nullable = false)
	private Long billNo;

	@PrePersist
	public void generateBillNo() {
	    if (this.billNo == null) {
	        this.billNo = ThreadLocalRandom.current().nextLong(1_000_000_000_000L, 9_999_999_999_999L);
	    }
	}

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	@NotBlank(message="The payment type cannot be left empty")
	private String paymentType;
	
	
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private Set<OrderProductEntity> products;
	
	@OneToOne(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private UserOrderEntity user;
	
	@Column(nullable = false)
	@DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be positive")
	@Digits(integer = 10, fraction = 2, message = "Total amount must be a valid currency value")
	private Long totalPrice;
	
	@Column(nullable = false)
	private boolean isDelivered;

	public OrderEntity(
			@DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be positive") @Digits(integer = 10, fraction = 2, message = "Total amount must be a valid currency value") Long totalPrice,
			boolean isDelivered) {
		this.totalPrice = totalPrice;
		this.isDelivered = isDelivered;
	}
	
	

	public OrderEntity() {
		this.totalPrice=0L;
		this.isDelivered=false;
		this.paymentType = "cash";
	}



	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getBillNo() {
		return billNo;
	}

	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Set<OrderProductEntity> getProducts() {
		return products;
	}
	 

	public void setProducts(Set<OrderProductEntity> products) {
		this.products = products;
	}

	public UserOrderEntity getUser() {
		return user;
	}

	public void setUser(UserOrderEntity user) {
		this.user = user;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}
	
	
	
	
	

	
}
