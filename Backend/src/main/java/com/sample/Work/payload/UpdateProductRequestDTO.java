package com.sample.Work.payload;


import jakarta.validation.constraints.*;

public class UpdateProductRequestDTO {
	
	    @NotNull(message = "Product ID must be provided")
	    private Long productId;

	    @NotBlank(message = "Product name cannot be empty")
	    private String productName;

	    @NotBlank(message = "Category cannot be empty")
	    private String category;

	    @NotNull(message = "Price cannot be null")
	    @Digits(integer = 10, fraction = 2, message = "Price format is invalid")
	    @Positive(message = "Price must be positive")
	    private Long price;

		public UpdateProductRequestDTO(@NotNull(message = "Product ID must be provided") Long productId,
				@NotBlank(message = "Product name cannot be empty") String productName,
				@NotBlank(message = "Category cannot be empty") String category,
				@NotNull(message = "Price cannot be null") @Digits(integer = 10, fraction = 2, message = "Price format is invalid") @Positive(message = "Price must be positive") Long price) {
			this.productId = productId;
			this.productName = productName;
			this.category = category;
			this.price = price;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
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
