package com.sample.Work.payload;

public class ProductResponseDetailsDTO {
	private Long productId;
    private String productName;
    private String category;
    private Long price;
    private int quantity;
    
	public ProductResponseDetailsDTO(Long productId, String productName, String category, Long price, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
	}
	
	public ProductResponseDetailsDTO() {
		super();
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
    
    
}
