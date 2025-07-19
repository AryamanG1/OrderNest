package com.sample.Work.payload;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDetailDTO {
	private Long orderId;
	private Long billNo;
	private LocalDateTime createdAt;
	private String paymentType;
	private boolean delivered;
	private List<ProductResponseDetailsDTO> products;
	
	public OrderResponseDetailDTO(Long orderId, Long billNo, LocalDateTime createdAt, String paymentType,
			boolean delivered, List<ProductResponseDetailsDTO> products) {
		super();
		this.orderId = orderId;
		this.billNo = billNo;
		this.createdAt = createdAt;
		this.paymentType = paymentType;
		this.delivered = delivered;
		this.products = products;
	}

	public OrderResponseDetailDTO() {
		super();
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

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public List<ProductResponseDetailsDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductResponseDetailsDTO> products) {
		this.products = products;
	}
	
	
	
	
	
}
