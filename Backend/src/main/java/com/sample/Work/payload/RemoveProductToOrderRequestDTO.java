package com.sample.Work.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RemoveProductToOrderRequestDTO {

	 @NotNull(message = "Order ID must not be null")
	    private Long orderId;

	 @NotNull(message = "Product ID must not be null")
	    private Long productId;

	@Min(value = 1, message = "Quantity must be at least 1")
	private int removeQuantity;

	public RemoveProductToOrderRequestDTO(@NotNull(message = "Order ID must not be null") Long orderId,
			@NotNull(message = "Product ID must not be null") Long productId,
			@Min(value = 1, message = "Quantity must be at least 1") int removeQuantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.removeQuantity = removeQuantity;
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

	public int getRemoveQuantity() {
		return removeQuantity;
	}

	public void setRemoveQuantity(int removeQuantity) {
		this.removeQuantity = removeQuantity;
	}
		
	
	
	
}
