package com.myKart.userActivity.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myKart.infra.constant.Constants;
import com.myKart.infra.dto.RequestWrapper;
import com.myKart.infra.dto.ResponseWrapper;
import com.myKart.userActivity.dto.ApiResponse;
import com.myKart.userActivity.dto.UserActivity;
import com.myKart.userActivity.service.UserActivityService;

@RestController
@RequestMapping("userActivity")
public class UserActivityController {

	@Autowired
	UserActivityService userActivityService;
	
	private static final Logger LOGGER = LogManager.getLogger(UserActivityController.class);

	// Endpoint To Add UserActivity (Orders,WishList,CartList)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "/addUserActivity")
	public ResponseEntity<Object> addUserActivity(@RequestBody RequestWrapper request) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			JSONObject requestObject = new JSONObject(request);
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = requestObject.get("requestBody").toString();
			UserActivity userActivity = objectMapper.readValue(requestBody, UserActivity.class);
			String response = userActivityService.addUserActivity(userActivity);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// EndPoint to get UserActivity
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/getUserActivity/{userId}")
	public ResponseEntity<Object> getUserActivity(@PathVariable String userId) throws Exception {
		LOGGER.info("Get call to retrieve user UserActivity for userId: " + userId);
//		ResponseWrapper responseWrapper = new ResponseWrapper();
		UserActivity response = userActivityService.getUserActivity(userId);
		return new ResponseEntity(new ApiResponse<>(response),HttpStatus.OK);
	}

	@GetMapping("/getWishList/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getWishList(@PathVariable String userId) throws Exception {
        LOGGER.info("Request to get wishlist for user: {}", userId);
        Map<String, Object> wishList = userActivityService.getWishList(userId);
        return new ResponseEntity<>(new ApiResponse<>(wishList), HttpStatus.OK);
    }

    @GetMapping("/getCartList/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCartList(@PathVariable String userId) throws Exception {
        LOGGER.info("Request to get cart for user: {}", userId);
        Map<String, Object> cartList = userActivityService.getCartList(userId);
        return new ResponseEntity<>(new ApiResponse<>(cartList), HttpStatus.OK);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getOrderList(@PathVariable String userId) throws Exception {
        LOGGER.info("Request to get orders for user: {}", userId);
        Map<String, Object> orderList = userActivityService.getOrderList(userId);
        return new ResponseEntity<>(new ApiResponse<>(orderList), HttpStatus.OK);
    }
    
    
    
	// EndPoint to update WishList
    @GetMapping("/addWishList/{userId}/{productId}")
    public ResponseEntity<ApiResponse<String>> addToWishList(@PathVariable String userId, @PathVariable String productId) throws Exception {
        LOGGER.info("Request to add productId: {} to wishlist for userId: {}", productId, userId);
        String response = userActivityService.addToWishList(userId, productId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.CREATED);
    }

    @GetMapping("/addCartList/{userId}/{productId}")
    public ResponseEntity<ApiResponse<String>> addToCartList(@PathVariable String userId, @PathVariable String productId) throws Exception {
        LOGGER.info("Request to add productId: {} to cart for userId: {}", productId, userId);
        String response = userActivityService.addToCartList(userId, productId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.CREATED);
    }

    @PostMapping("/orders/add/{userId}/{productId}")
    public ResponseEntity<ApiResponse<String>> addToOrderList(@PathVariable String userId, @PathVariable String productId) throws Exception {
        LOGGER.info("Request to add productId: {} to orders for userId: {}", productId, userId);
        String response = userActivityService.addToOrderList(userId, productId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.CREATED);
    }

    // --- DELETE Endpoints (Using DELETE) ---
    @DeleteMapping("/wishlist/delete/{userId}/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteFromWishList(@PathVariable String userId, @PathVariable String productId) throws Exception {
        LOGGER.info("Request to delete productId: {} from wishlist for userId: {}", productId, userId);
        String response = userActivityService.deleteFromWishList(userId, productId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/cart/delete/{userId}/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteFromCartList(@PathVariable String userId, @PathVariable String productId) throws Exception {
        LOGGER.info("Request to delete productId: {} from cart for userId: {}", productId, userId);
        String response = userActivityService.deleteFromCartList(userId, productId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/orders/delete/{userId}/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteFromOrderList(@PathVariable String userId, @PathVariable String productId) throws Exception {
        LOGGER.info("Request to delete productId: {} from orders for userId: {}", productId, userId);
        String response = userActivityService.deleteFromOrderList(userId, productId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }
	
	
}
