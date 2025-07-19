package com.sample.Work.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.Work.model.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long>{
	List<ProductEntity> findByCategoryAndPriceBetween(String category, Long lowerPrice, Long upperPrice);
}
