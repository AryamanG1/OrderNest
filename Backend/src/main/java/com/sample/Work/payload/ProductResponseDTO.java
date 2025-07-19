package com.sample.Work.payload;

import com.sample.Work.model.ProductEntity;

public class ProductResponseDTO {
    
	
	private Long productId;
    private String productName;
    private String category;
    private Long price;
    
	public ProductResponseDTO(Long productId, String productName, String category, Long price) {
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.price = price;
	}
	
	public ProductResponseDTO(ProductEntity product) {
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.category = product.getCategory();
		this.price = product.getPrice();
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
