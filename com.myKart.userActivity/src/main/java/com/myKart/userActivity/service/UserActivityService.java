package com.myKart.userActivity.service;

import java.util.List;
import java.util.stream.IntStream;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.myKart.infra.exception.BussinessException;
import com.myKart.userActivity.dto.UserActivity;
import com.myKart.userActivity.repository.UserActivityRepository;

@Service
public class UserActivityService {

	@Autowired
	UserActivityRepository userActivityRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	public String addUserActivity(UserActivity userActivity) throws Exception {
		try {
			userActivityRepository.save(userActivity);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "Added";
	}
	
	@Cacheable(value = "getUserActivity")
	public UserActivity getUserActivity(String userId) throws Exception {
		UserActivity userActivity;
		try {
			userActivity = userActivityRepository.findByUserId(userId);
			if(userActivity==null) {
				throw new BussinessException("User Activity Not Found");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return userActivity;
	}
	
	@Cacheable(value = "getWishList")
	public List<String> getWishList(String userId) throws Exception {
		List<String> wishList;
		try {
			wishList = userActivityRepository.findWishListByUserId(userId);
			if(wishList.size()==0) {
				wishList.add("WishList Empty");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return wishList;
	}
	
	@Cacheable(value = "getCartList")
	public List<String> getCartList(String userId) throws Exception {
		List<String> cardList;
		try {
			cardList = userActivityRepository.findCartListByUserId(userId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return cardList;
	}
	
	@Cacheable(value = "getOrderList")
	public List<String> getOrderList(String userId) throws Exception {
		List<String> orderList;
		try {
			orderList = userActivityRepository.findOrderListByUserId(userId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return orderList;
	}

	public String addToWishList(String userId, String productId) throws Exception {
		List<String> wishList;
		try {
			wishList = getWishList(userId);
			JSONObject jsonObject = new JSONObject(wishList.get(0));
			JSONArray wishListArray = jsonObject.getJSONArray("wishList");
			boolean isPresent = IntStream.range(0, wishListArray.length())
	                .mapToObj(wishListArray::getString)
	                .anyMatch(productId::equals);
			if(isPresent) {
				return "Item Already Present in WishList";
			}
			wishListArray.put(productId);
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update();
			update.set("wishList", wishListArray.toList());
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "Wish List Updated";
	}
	
	public String addToOrderList(String userId, String productId) throws Exception {
		List<String> OrderList;
		try {
			OrderList = getOrderList(userId);
			JSONObject jsonObject = new JSONObject(OrderList.get(0));
			JSONArray OrderListArray = jsonObject.getJSONArray("orderList");
			OrderListArray.put(productId);
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update();
			update.set("orderList", OrderListArray.toList());
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "Order List Updated";
	}
	
	public String addToCartList(String userId, String productId) throws Exception {
		List<String> CartList;
		try {
			CartList = getCartList(userId);
			JSONObject jsonObject = new JSONObject(CartList.get(0));
			JSONArray CartListArray = jsonObject.getJSONArray("cartList");
			boolean isPresent = IntStream.range(0, CartListArray.length())
	                .mapToObj(CartListArray::getString)
	                .anyMatch(productId::equals);
			if(isPresent) {
				return "Item Already Present in Cart";
			}
			CartListArray.put(productId);
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update();
			update.set("cartList", CartListArray.toList());
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "Cart List Updated";
	}

	public String deleteFromWishList(String userId,String productId) throws Exception{
		try {

			 Query query = new Query(Criteria.where("userId").is(userId));
			 Update update = new Update().pull("wishList", productId);
			 mongoTemplate.updateFirst(query, update, UserActivity.class);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "Item Removed From WishList";
	}
	
	public String deleteFromCartList(String userId,String productId) throws Exception{
		try {;
			 Query query = new Query(Criteria.where("userId").is(userId));
			 Update update = new Update().pull("cartList", productId);
			 mongoTemplate.updateFirst(query, update, UserActivity.class);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "Item Removed From CartList";
	}
	
	public String deleteFromOrderList(String userId,String productId) throws Exception{
		try {
			 Query query = new Query(Criteria.where("userId").is(userId));
			 Update update = new Update().pull("orderList", productId);
			 mongoTemplate.updateFirst(query, update, UserActivity.class);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return "Item Removed From CartList";
	}
	
}
