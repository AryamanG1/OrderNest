package com.sample.Work.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateProductRequestDTO {
	@NotBlank(message="Product Name cannot be empty")
	private String productName;
	
	@NotBlank(message="Category cannot be empty")
	private String category;
	
	@NotNull(message="Price cannot be empty")
	private Long price;
	
	

	public CreateProductRequestDTO(@NotBlank(message = "Product Name cannot be empty") String productName,
			@NotBlank(message = "Category cannot be empty") String category,
			@NotNull(message = "Price cannot be empty") Long price) {
		this.productName = productName;
		this.category = category;
		this.price = price;
	}
	
	

	public CreateProductRequestDTO() {
	}



	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	 
}
