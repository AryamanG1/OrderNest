package com.sample.Work.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="products")
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productId;
	
	@NotBlank(message="Product Name cannot be empty")
	private String productName;
	
	@NotBlank(message="Category cannot be empty")
	private String category;
	
	@NotNull(message="Price cannot be empty")
	private Long price;
	
	@OneToMany(mappedBy="product",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonManagedReference
	private Set<OrderProductEntity> orders = new HashSet<>();

	
	public ProductEntity(@NotBlank(message = "Product Name cannot be empty") String productName,
			@NotBlank(message = "Category cannot be empty") String category,
			@NotNull(message = "Price cannot be empty") Long price) {
		this.productName = productName;
		this.category = category;
		this.price = price;
	}
	
	

	public ProductEntity() {
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

	public Set<OrderProductEntity> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderProductEntity> orders) {
		this.orders = orders;
	}
	
	
	
	
	
}
