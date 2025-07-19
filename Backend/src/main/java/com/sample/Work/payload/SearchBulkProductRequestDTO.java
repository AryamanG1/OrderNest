package com.sample.Work.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SearchBulkProductRequestDTO {
	@NotBlank(message="Category cannot be empty")
	private String category;
	
	@NotNull(message="Price cannot be empty")
	private Long LowerPrice;
	
	@NotNull(message="Price cannot be empty")
	private Long UpperPrice;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getLowerPrice() {
		return LowerPrice;
	}

	public void setLowerPrice(Long lowerPrice) {
		LowerPrice = lowerPrice;
	}

	public Long getUpperPrice() {
		return UpperPrice;
	}

	public void setUpperPrice(Long upperPrice) {
		UpperPrice = upperPrice;
	}

	public SearchBulkProductRequestDTO(@NotBlank(message = "Category cannot be empty") String category,
			@NotNull(message = "Price cannot be empty") Long lowerPrice,
			@NotNull(message = "Price cannot be empty") Long upperPrice) {
		this.category = category;
		LowerPrice = lowerPrice;
		UpperPrice = upperPrice;
	}
	
	
	
	
}
