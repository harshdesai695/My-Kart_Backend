package com.mykart.seller.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mykart.seller.dto.Seller;

@Repository
public interface SellerRepository extends MongoRepository<Seller,String> {
	
	Seller findBySellerId(String id);
	Seller findBySellerEmailAndSellerPassword(String email,String password);
	Seller findBySellerEmail(String email);
    // You can add other finders if needed
	Seller findBySellerName(String name);

	
}
