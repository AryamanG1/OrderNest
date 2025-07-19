package com.sample.Work.service;

import java.util.List;

import com.sample.Work.model.ProductEntity;
import com.sample.Work.payload.CreateProductRequestDTO;
import com.sample.Work.payload.DeleteProductRequestDTO;
import com.sample.Work.payload.FindProductRequestDTO;
import com.sample.Work.payload.ProductResponseDTO;
import com.sample.Work.payload.SearchBulkProductRequestDTO;
import com.sample.Work.payload.UpdateProductRequestDTO;

public interface ProductService {
	    ProductResponseDTO createProduct(CreateProductRequestDTO request);
	    ProductResponseDTO updateProduct(UpdateProductRequestDTO request);
	    ProductResponseDTO deleteProduct(DeleteProductRequestDTO request);
	    ProductResponseDTO getProductById(FindProductRequestDTO request);
	    List<ProductResponseDTO> getAllProducts();
	    List<ProductResponseDTO> searchProducts(SearchBulkProductRequestDTO request );
}
