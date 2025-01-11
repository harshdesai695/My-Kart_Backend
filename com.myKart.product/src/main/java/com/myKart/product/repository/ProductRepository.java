package com.myKart.product.repository;

import java.util.List;

//import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.myKart.product.dto.Product;


public interface ProductRepository extends MongoRepository<Product, String> {
	Product findByProductId(String productId);
//	List<Product> FindAll();
	@Query("{ 'brandName': { $regex: ?0, $options: 'i' } }")
    List<Product> findByBrandNameContaining(String brandName);
}
