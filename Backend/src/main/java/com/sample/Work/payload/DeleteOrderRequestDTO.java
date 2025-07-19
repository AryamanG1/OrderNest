package com.sample.Work.payload;

import jakarta.validation.constraints.NotNull;

public class DeleteOrderRequestDTO {
	
		@NotNull(message="Order ID cannot be left null")
	    private Long orderId;

		public DeleteOrderRequestDTO(@NotNull(message = "Order ID cannot be left null") Long orderId) {
			this.orderId = orderId;
		}

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
	
		
		
}
