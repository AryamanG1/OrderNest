package com.sample.Work.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddProductToOrderRequestDTO {

	  private Long orderId;

	 @NotNull(message = "Product ID must not be null")
	    private Long productId;

	@Min(value = 1, message = "Quantity must be at least 1")
	private int quantity;
	
	

	public AddProductToOrderRequestDTO(@NotNull(message = "Order ID must not be null") Long orderId,
			@NotNull(message = "Product ID must not be null") Long productId,
			@Min(value = 1, message = "Quantity must be at least 1") int quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
