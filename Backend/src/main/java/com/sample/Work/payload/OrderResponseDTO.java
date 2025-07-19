package com.sample.Work.payload;

import java.time.LocalDateTime;

import com.sample.Work.model.OrderEntity;

public class OrderResponseDTO {

	private Long orderId;
	private Long billNo;
	private LocalDateTime createdAt;
	private String paymentType;
	private boolean delivered;
	
	public OrderResponseDTO(Long orderId, Long billNo, LocalDateTime createdAt, String paymentType, boolean delivered) {
		this.orderId = orderId;
		this.billNo = billNo;
		this.createdAt = createdAt;
		this.paymentType = paymentType;
		this.delivered = delivered;
	}
	
	public OrderResponseDTO(OrderEntity order){
		this.orderId=order.getOrderId();
		this.billNo=order.getBillNo();
		this.createdAt=order.getCreatedAt();
		this.paymentType=order.getPaymentType();
		this.delivered=order.isDelivered();
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
	
	
	

}
