package com.myKart.userActivity.service;

import com.myKart.infra.exception.BussinessException;
import com.myKart.userActivity.dto.UserActivity;
import com.myKart.userActivity.repository.UserActivityRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
			throw new Exception("Error while adding user activity: " + e.getMessage());
		}
		return "Added";
	}

	public UserActivity getUserActivity(String userId) throws Exception {
		try {
			UserActivity userActivity = userActivityRepository.findByUserId(userId);
			if (userActivity == null) {
				throw new BussinessException("User Activity Not Found");
			}
			return userActivity;
		} catch (BussinessException e) {
			throw new BussinessException("No User Activity Found"+e.getMessage());
		} catch (Exception e) {
			throw new Exception("Error while retrieving user activity: " + e.getMessage());
		}
	}

	public Map<String, Object> getWishList(String userId) throws Exception {
		try {
			List<String> wishListResult = userActivityRepository.findWishListByUserId(userId);
			if (wishListResult == null || wishListResult.isEmpty()) {
				throw new BussinessException("WishList is empty for user ID: " + userId);
			}
			// Parse the JSON string into a Map
			String jsonString = wishListResult.get(0);
			JSONObject jsonObject = new JSONObject(jsonString);
			return jsonObject.toMap();
		} catch (BussinessException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error while retrieving wishlist: " + e.getMessage());
		}
	}

	/**
	 * Retrieves the user's cart as a proper JSON object.
	 */
	public Map<String, Object> getCartList(String userId) throws Exception {
		try {
			List<String> cartListResult = userActivityRepository.findCartListByUserId(userId);
			if (cartListResult == null || cartListResult.isEmpty()) {
				throw new BussinessException("Cart is empty for user ID: " + userId);
			}
			// Parse the JSON string into a Map
			String jsonString = cartListResult.get(0);
			JSONObject jsonObject = new JSONObject(jsonString);
			return jsonObject.toMap();
		} catch (BussinessException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error while retrieving cart list: " + e.getMessage());
		}
	}

	/**
	 * Retrieves the user's orders as a proper JSON object.
	 */
	public Map<String, Object> getOrderList(String userId) throws Exception {
		try {
			List<String> orderListResult = userActivityRepository.findOrderListByUserId(userId);
			if (orderListResult == null || orderListResult.isEmpty()) {
				throw new BussinessException("No orders found for user ID: " + userId);
			}
			// Parse the JSON string into a Map
			String jsonString = orderListResult.get(0);
			JSONObject jsonObject = new JSONObject(jsonString);
			return jsonObject.toMap();
		} catch (BussinessException e) {
			throw e;
		} catch (Exception e) {
			throw new Exception("Error while retrieving order list: " + e.getMessage());
		}
	}

	public String addToWishList(String userId, String productId) throws Exception {
		try {
			List<String> wishList = userActivityRepository.findWishListByUserId(userId);
			if (wishList.isEmpty()) {
				wishList.add("{\"wishList\":[]}");
			}
			JSONObject jsonObject = new JSONObject(wishList.get(0));
			JSONArray wishListArray = jsonObject.getJSONArray("wishList");

			boolean isPresent = IntStream.range(0, wishListArray.length()).mapToObj(wishListArray::getString)
					.anyMatch(productId::equals);

			if (isPresent) {
				return "Item Already Present in WishList";
			}

			wishListArray.put(productId);
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update().set("wishList", wishListArray.toList());
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		}  catch (Exception e) {
			throw new Exception("Error while updating Wishlist: " + e.getMessage());
		}
		return "Wish List Updated";
	}

	public String addToOrderList(String userId, String productId) throws Exception {
		try {
			Map<String, Object> orderList = getOrderList(userId);
			
			JSONObject jsonObject = new JSONObject(orderList.get(0));
			JSONArray orderListArray = jsonObject.getJSONArray("orderList");
			orderListArray.put(productId);

			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update().set("orderList", orderListArray.toList());
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		} catch (Exception e) {
			throw new Exception("Error while updating Order List: " + e.getMessage());
		}
		return "Order List Updated";
	}

	public String addToCartList(String userId, String productId) throws Exception {
		try {
			List<String> cartList = userActivityRepository.findCartListByUserId(userId);
			if (cartList.isEmpty()) {
				cartList.add("{\"cartList\":[]}");
			}
			JSONObject jsonObject = new JSONObject(cartList.get(0));
			JSONArray cartListArray = jsonObject.getJSONArray("cartList");

			boolean isPresent = IntStream.range(0, cartListArray.length()).mapToObj(cartListArray::getString)
					.anyMatch(productId::equals);

			if (isPresent) {
				return "Item Already Present in Cart";
			}

			cartListArray.put(productId);
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update().set("cartList", cartListArray.toList());
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		}  catch (Exception e) {
			throw new Exception("Error while updating Cart List: " + e.getMessage());
		}
		return "Cart List Updated";
	}

	public String deleteFromWishList(String userId, String productId) throws Exception {
		try {
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update().pull("wishList", productId);
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		} catch (Exception e) {
			throw new Exception("Error while removing from Wishlist: " + e.getMessage());
		}
		return "Item Removed From WishList";
	}

	public String deleteFromCartList(String userId, String productId) throws Exception {
		try {
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update().pull("cartList", productId);
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		} catch (Exception e) {
			throw new Exception("Error while removing from Cart: " + e.getMessage());
		}
		return "Item Removed From CartList";
	}

	public String deleteFromOrderList(String userId, String productId) throws Exception {
		try {
			Query query = new Query(Criteria.where("userId").is(userId));
			Update update = new Update().pull("orderList", productId);
			mongoTemplate.updateFirst(query, update, UserActivity.class);
		} catch (Exception e) {
			throw new Exception("Error while removing from Order List: " + e.getMessage());
		}
		return "Item Removed From OrderList";
	}
}