package com.sample.Work.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sample.Work.response.ApiResponse;
import com.sample.Work.service.OrderServiceImpl;
import com.sample.Work.service.ProductService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.sample.Work.payload.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Product Controller", description = "Handles product management functionalities")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}



	@PostMapping("/product")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<ApiResponse<?>> createProduct(@RequestBody @Valid CreateProductRequestDTO productReq){
		try {
			ProductResponseDTO response = productService.createProduct(productReq);
			return ResponseEntity.ok(ApiResponse.success("Successfully created Product", response));
		}
		catch(ResponseStatusException e){
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	@PutMapping("/product")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<ApiResponse<?>> updateProduct(@RequestBody @Valid UpdateProductRequestDTO product){
		try {
			ProductResponseDTO response = productService.updateProduct(product);
			return ResponseEntity.ok(ApiResponse.success("Successfully updated Product", response));
		}
		catch(ResponseStatusException e){
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	@DeleteMapping("/product")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<ApiResponse<?>> deleteProduct(@RequestBody @Valid DeleteProductRequestDTO product){
		try {
			ProductResponseDTO response = productService.deleteProduct(product);
			return ResponseEntity.ok(ApiResponse.success("Successfully deleted Product", response));
		}
		catch(ResponseStatusException e){
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	
	@GetMapping("/product/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ApiResponse<?>> getProductById(@PathVariable Long id) {
	    try {
	        ProductResponseDTO response = productService.getProductById(new FindProductRequestDTO(id));
	        return ResponseEntity.ok(ApiResponse.success("Successfully found Product", response));
	    } catch (ResponseStatusException e) {
	    	logger.error("Encountered Response Status Exception : {}",e.getReason());
	        return new ResponseEntity<>(ApiResponse.failure(e.getReason()), e.getStatusCode());
	    }
	}

	
	@PostMapping("/product/search")
	@PreAuthorize("hasAnyRole('USER','SELLER')")
	public ResponseEntity<ApiResponse<?>> searchProduct(@RequestBody SearchBulkProductRequestDTO request){
		try {
			List<ProductResponseDTO> response = productService.searchProducts(request);
			return ResponseEntity.ok(ApiResponse.success("Successfully found Product", response));
		}
		catch(ResponseStatusException e){
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	@GetMapping("/product/all")
	@PreAuthorize("hasAnyRole('USER','SELLER','ADMIN')")
	public ResponseEntity<ApiResponse<?>> searchProductAll(){
		try {
			List<ProductResponseDTO> response = productService.getAllProducts();
			return ResponseEntity.ok(ApiResponse.success("Successfully found all products", response));
		}
		catch(ResponseStatusException e){
			logger.error("Encountered Response Status Exception : {}",e.getReason());
			return new ResponseEntity<>(ApiResponse.failure(e.getReason()),e.getStatusCode());
		}
	}
	
	
	
	
	
	
	

}
