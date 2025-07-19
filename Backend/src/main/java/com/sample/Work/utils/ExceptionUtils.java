package com.sample.Work.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.Work.model.OrderProductId;
import com.sample.Work.security.jwt.AuthEntryPointJwt;

public class ExceptionUtils {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	public static RuntimeException userNotFoundError(String username){
		logger.error("User not found for username: {}",username);
		return new RuntimeException("User Not Found");
	}
	
	public static RuntimeException orderNotFoundError(Long orderID) {
		logger.error("Order not found for order ID : {}" , orderID);
		return new RuntimeException("Order Not Found");
	}
	
	public static RuntimeException productNotFoundError(Long productID) {
		logger.error("Product not found for order ID : {}" , productID);
		return new RuntimeException("Product Not Found");
	}
	
	public static RuntimeException orderProductNotFoundError(OrderProductId ID) {
		logger.error("Order Product mapping not found for ID : {}" , ID);
		return new RuntimeException("Order Product not found");
	}
	
	public static RuntimeException userOrderNotFoundError(OrderProductId ID) {
		logger.error("Order Product mapping not found for ID : {}" , ID);
		return new RuntimeException("Order Product not found");
	}
	
	public static RuntimeException tokenNotFoundError(String JWT) {
		logger.error("Token Mapping not found for ID : {}" , JWT);
		return new RuntimeException("Token not found");
	}
	
	
}
