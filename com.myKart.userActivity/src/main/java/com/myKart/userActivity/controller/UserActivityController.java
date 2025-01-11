package com.myKart.userActivity.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.myKart.infra.exception.BussinessException;
import com.myKart.userActivity.dto.UserActivity;
import com.myKart.userActivity.service.UserActivityService;

@RestController
@RequestMapping("userActivity")
public class UserActivityController {

	@Autowired
	UserActivityService userActivityService;

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
	public ResponseEntity<Object> getUserActivity(@PathVariable String userId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			UserActivity response = userActivityService.getUserActivity(userId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (BussinessException e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// EndPoint To get WishList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/getWishList/{userId}")
	public ResponseEntity<Object> getWishList(@PathVariable String userId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			List<String> response = userActivityService.getWishList(userId);
			responseWrapper.setResponseBody(response.get(0));
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// EndPoint To get CartList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/getCartList/{userId}")
	public ResponseEntity<Object> getCartList(@PathVariable String userId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			List<String> response = userActivityService.getCartList(userId);
			responseWrapper.setResponseBody(response.get(0));
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// EndPoint To get OrderList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/getOrderList/{userId}")
	public ResponseEntity<Object> getOrderList(@PathVariable String userId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			List<String> response = userActivityService.getOrderList(userId);
			responseWrapper.setResponseBody(response.get(0));
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// EndPoint to update WishList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/addWishList/{userId}/{productId}")
	public ResponseEntity<Object> addToWishList(@PathVariable String userId, @PathVariable String productId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
//			System.out.println("UserID:"+userId);
//			System.out.println("productId:"+productId);
			String response = userActivityService.addToWishList(userId, productId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// EndPoint to update OrderList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/addOrderList/{userId}/{productId}")
	public ResponseEntity<Object> addToOrderList(@PathVariable String userId, @PathVariable String productId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			String response = userActivityService.addToOrderList(userId, productId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// EndPoint to update CartList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/addCartList/{userId}/{productId}")
	public ResponseEntity<Object> addToCartList(@PathVariable String userId, @PathVariable String productId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			String response = userActivityService.addToCartList(userId, productId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}
	
	//Delete Item from WishList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/deleteWishList/{userId}/{productId}")
	public ResponseEntity<Object> deleteFromWishList(@PathVariable String userId, @PathVariable String productId){
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			String response = userActivityService.deleteFromWishList(userId, productId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}
	
	//Delete Item from WishList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/deleteCartList/{userId}/{productId}")
	public ResponseEntity<Object> deleteFromCartList(@PathVariable String userId, @PathVariable String productId){
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			String response = userActivityService.deleteFromCartList(userId, productId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}
	
	//Delete Item from WishList
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/deleteOrderList/{userId}/{productId}")
	public ResponseEntity<Object> deleteFromOrderList(@PathVariable String userId, @PathVariable String productId){
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			String response = userActivityService.deleteFromOrderList(userId, productId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}
	
	
}
