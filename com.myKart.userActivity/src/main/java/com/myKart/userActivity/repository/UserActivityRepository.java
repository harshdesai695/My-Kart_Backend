package com.myKart.userActivity.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.myKart.userActivity.dto.UserActivity;


@Repository
public interface UserActivityRepository extends MongoRepository<UserActivity, List<String>>{
	
	UserActivity findByUserId(String userId);
	@Query(value = "{ 'userId': ?0 }", fields = "{ 'wishList' : 1, '_id' : 0 }")
	List<String> findWishListByUserId(String userId); 
	@Query(value = "{ 'userId': ?0 }", fields = "{ 'cartList' : 1, '_id' : 0 }")
	List<String> findCartListByUserId(String userId);
	@Query(value = "{ 'userId': ?0 }", fields = "{ 'orderList' : 1, '_id' : 0 }")
	List<String> findOrderListByUserId(String userId);
    
//    void updateWishListByUserId(String userId, String productId);
}
