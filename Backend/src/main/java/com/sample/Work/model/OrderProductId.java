package com.sample.Work.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
	public class OrderProductId implements Serializable {

	    private Long orderId;
	    private Long productId;

	    public OrderProductId() {}

	    public OrderProductId(Long orderId, Long productId) {
	        this.orderId = orderId;
	        this.productId = productId;
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

		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof OrderProductId)) return false;
	        OrderProductId that = (OrderProductId) o;
	        return Objects.equals(orderId, that.orderId) &&
	               Objects.equals(productId, that.productId);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(orderId, productId);
	    }
	}


