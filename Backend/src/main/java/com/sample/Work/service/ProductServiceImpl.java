package com.sample.Work.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sample.Work.model.ProductEntity;
import com.sample.Work.payload.CreateProductRequestDTO;
import com.sample.Work.payload.DeleteProductRequestDTO;
import com.sample.Work.payload.FindProductRequestDTO;
import com.sample.Work.payload.ProductResponseDTO;
import com.sample.Work.payload.SearchBulkProductRequestDTO;
import com.sample.Work.payload.UpdateProductRequestDTO;
import com.sample.Work.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO createProduct(CreateProductRequestDTO request) {
        logger.trace("Entered createProduct service");
        ProductEntity product = new ProductEntity(request.getProductName(), request.getCategory(), request.getPrice());
        productRepository.save(product);
        logger.debug("Created and saved product: {}", product);
        logger.trace("Exited createProduct service");
        return new ProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(UpdateProductRequestDTO request) {
        logger.trace("Entered updateProduct service for productId: {}", request.getProductId());
        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> {
                    logger.error("Product not found with id: {}", request.getProductId());
                    return new RuntimeException("Product not found");
                });

        product.setProductName(request.getProductName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        productRepository.save(product);
        logger.debug("Updated product: {}", product);
        logger.trace("Exited updateProduct service");
        return new ProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO deleteProduct(DeleteProductRequestDTO request) {
        logger.trace("Entered deleteProduct service for productId: {}", request.getProductId());
        ProductEntity productDelete = productRepository.findById(request.getProductId())
                .orElseThrow(() -> {
                    logger.error("Product not found for deletion with id: {}", request.getProductId());
                    return new RuntimeException("Product does not exist");
                });

        productRepository.delete(productDelete);
        logger.debug("Deleted product: {}", productDelete);
        logger.trace("Exited deleteProduct service");
        return new ProductResponseDTO(productDelete);
    }

    @Override
    public ProductResponseDTO getProductById(FindProductRequestDTO request) {
        logger.trace("Entered getProductById service for productId: {}", request.getProductId());
        ProductEntity productFind = productRepository.findById(request.getProductId())
                .orElseThrow(() -> {
                    logger.error("Product not found with id: {}", request.getProductId());
                    return new RuntimeException("Product does not exist");
                });

        logger.debug("Found product: {}", productFind);
        logger.trace("Exited getProductById service");
        return new ProductResponseDTO(productFind);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        logger.trace("Entered getAllProducts service");
        List<ProductResponseDTO> products = productRepository.findAll()
                .stream().map(ProductResponseDTO::new).collect(Collectors.toList());
        logger.debug("Fetched {} product(s)", products.size());
        logger.trace("Exited getAllProducts service");
        return products;
    }

    @Override
    public List<ProductResponseDTO> searchProducts(SearchBulkProductRequestDTO request) {
        logger.trace("Entered searchProducts service with category: {}, price range: {} - {}",
                request.getCategory(), request.getLowerPrice(), request.getUpperPrice());

        List<ProductResponseDTO> result = productRepository
                .findByCategoryAndPriceBetween(request.getCategory(), request.getLowerPrice(), request.getUpperPrice())
                .stream().map(ProductResponseDTO::new).collect(Collectors.toList());

        logger.debug("Found {} product(s) matching filter", result.size());
        logger.trace("Exited searchProducts service");
        return result;
    }
}

