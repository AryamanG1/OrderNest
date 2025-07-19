package com.sample.Work.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;



@Entity
public class OrderProductEntity {

	@Id
	@EmbeddedId
	private OrderProductId id = new OrderProductId();
	
	@ManyToOne
	@MapsId("orderId")
	@JoinColumn(name="order_id" , nullable = false)
	@JsonBackReference
	private OrderEntity order;
	
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name="product_id", nullable = false)
	@JsonBackReference
	private ProductEntity product;
	
    @Column(nullable = false)
	private int quantity;

	public OrderProductEntity(OrderEntity order, ProductEntity product, int quantity) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}

	
	public OrderProductEntity() {
	}


	public OrderProductId getId() {
		return id;
	}

	public void setId(OrderProductId id) {
		this.id = id;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
    
    
    
    
    
	
}
