package com.sample.Work.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sample.Work.model.OrderEntity;
import com.sample.Work.payload.AddProductToOrderRequestDTO;
import com.sample.Work.payload.DeleteOrderRequestDTO;
import com.sample.Work.payload.OrderResponseDTO;
import com.sample.Work.payload.OrderResponseDetailDTO;
import com.sample.Work.payload.RemoveProductToOrderRequestDTO;
import com.sample.Work.payload.UpdateProductInOrderRequestDTO;
import com.sample.Work.response.ApiResponse;
import com.sample.Work.service.OrderService;
import com.sample.Work.service.OrderServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Order Controller", description = "Handles Order Management functionality")
public class OrderController {
	
private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
@Autowired
private OrderService orderService;

public OrderController(OrderService orderService) {
	this.orderService = orderService;
}

@PostMapping("/order")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<ApiResponse<?>> createOrder(){
try {
	OrderResponseDTO o = orderService.createNewOrder();
	
	return ResponseEntity.ok(ApiResponse.success("Successfully created new order", o));
}
catch(ResponseStatusException e){
	logger.error("Encountered Response Status Exception : {}",e.getReason());
	return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
}}

@PostMapping("/order/item")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<ApiResponse<?>> addProduct(@RequestBody @Valid AddProductToOrderRequestDTO product){
	try{
		OrderEntity order = orderService.addItemToOrder(product);
		return ResponseEntity.ok(ApiResponse.success("Successfully added item to order",order ));
	}
	catch(ResponseStatusException e){
		logger.error("Encountered Response Status Exception : {}",e.getReason());
		return new ResponseEntity<>(ApiResponse.failure(e.getMessage()),e.getStatusCode());
	}
}

@PutMapping("/order/item")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<ApiResponse<?>> updateProduct(@RequestBody @Valid UpdateProductInOrderRequestDTO product){
	try{
		OrderEntity order = orderService.updateItemInOrder(product);
		return ResponseEntity.ok(ApiResponse.success("Successfully updated item in order",order ));
	}
	catch(ResponseStatusException e){
		logger.error("Encountered Response Status Exception : {}",e.getReason());
		return new ResponseEntity<>(ApiResponse.failure(e.getMessage()),e.getStatusCode());
	}
}

@DeleteMapping("/order/item")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<ApiResponse<?>> deleteProduct(@RequestBody @Valid RemoveProductToOrderRequestDTO product){
	try{
		OrderEntity order = orderService.removeItemFromOrder(product);
		return ResponseEntity.ok(ApiResponse.success("Successfully deleted item in order", order ));
	}
	catch(ResponseStatusException e){
		logger.error("Encountered Response Status Exception : {}",e.getReason());
		return new ResponseEntity<>(ApiResponse.failure(e.getMessage()),e.getStatusCode());
	}
}

@DeleteMapping("/order")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<ApiResponse<?>> deleteOrder(@RequestBody @Valid DeleteOrderRequestDTO orderDTO){
	try {
		OrderResponseDTO orderDel = orderService.deleteOrder(orderDTO);
		return ResponseEntity.ok(ApiResponse.success("Successfully Deleted Order", orderDel));
	}
	catch(ResponseStatusException e){
		logger.error("Encountered Response Status Exception : {}",e.getReason());
		return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
	}
}

@GetMapping("/order")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<ApiResponse<?>> getOrder(){
	try {
		List<OrderResponseDTO> ans = orderService.displayOrderHistoryUser();
		return ResponseEntity.ok(ApiResponse.success("Successfully Found Orders", ans));
	}
	catch(ResponseStatusException e){
		logger.error("Encountered Response Status Exception : {}",e.getReason());
		return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
	}
}


@GetMapping("/admin/order")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<?>> displayAllOrder(){
	try {
		List<OrderResponseDTO> ans = orderService.displayAllOrderAdmin();
		return ResponseEntity.ok(ApiResponse.success("Successfully Found Orders", ans));
	}
	catch(ResponseStatusException e){
		logger.error("Encountered Response Status Exception : {}",e.getReason());
		return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
	}
}

@GetMapping("/order/{id}")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<ApiResponse<?>> getOrderDetails(@PathVariable Long id){
	try {
		OrderResponseDetailDTO ans = orderService.displayOrderDetailsUser(id);
		return ResponseEntity.ok(ApiResponse.success("Successfully Found Order details", ans));
	}
	catch(ResponseStatusException e){
		logger.error("Encountered Response Status Exception : {}",e.getReason());
		return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
	}
}

}
