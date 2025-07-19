package com.sample.Work.payload;

import jakarta.validation.constraints.NotNull;

public class FindProductRequestDTO {
	@NotNull(message = "Product ID must be provided")
    private Long productId;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public FindProductRequestDTO(@NotNull(message = "Product ID must be provided") Long productId) {
		this.productId = productId;
	}

	
}
