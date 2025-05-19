package com.myKart.user.controller;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myKart.infra.constant.Constants;
import com.myKart.infra.dto.RequestWrapper;
import com.myKart.infra.dto.ResponseWrapper;
import com.myKart.infra.exception.BussinessException;
import com.myKart.user.dto.User;
import com.myKart.user.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	// POST EndPoint for Adding New User
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "user/addUser")
	public ResponseEntity<Object> addUser(@RequestBody RequestWrapper request) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			LOGGER.info("Incoming Request:"+request.toString());
			JSONObject requestObject = new JSONObject(request);
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody=requestObject.get("requestBody").toString();
			User user = objectMapper.readValue(requestBody, User.class);
			String response = userService.addUser(user);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			LOGGER.info("Exception:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}

		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// POST Endpoing for Login User
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "user/login")
	public ResponseEntity<Object> loginUser(@RequestBody RequestWrapper request) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			LOGGER.info("Incoming Request:"+request.toString());
			JSONObject requestObject = new JSONObject(request);
			JSONObject requestBodyObject=new JSONObject(requestObject.get("requestBody").toString());
			User response = userService.login(requestBodyObject.getString("userName"),requestBodyObject.getString("password"));
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		}catch(BussinessException e) {
			LOGGER.info("BussinessException:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		}
		catch (Exception e) {
			LOGGER.info("Exception:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	//GET EndPoint for Getting User Data
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "user/getUser/{userId}")
	public ResponseEntity<Object> getUser(@PathVariable String userId) {
		User user;
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			LOGGER.info("Get Call to get User data :"+userId);
			user = userService.getUserById(userId);
			responseWrapper.setResponseBody(user);
			responseWrapper.setResponseStatus(Constants.OK);
			LOGGER.info("Response:"+responseWrapper.toString());
		} catch(BussinessException e) {
			LOGGER.info("BussinessException:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		}catch (Exception e) {
			LOGGER.info("Exception:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	//Get EndPoint for Deleting Existing User
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "user/deleteUser/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable String userId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			LOGGER.info("Get Call to Delete User data :"+userId);
			String response = userService.deleteUser(userId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} 
		catch(BussinessException e) {
			LOGGER.info("BussinessException:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		}catch (Exception e) {
			LOGGER.info("Exception:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	
	//EndPoint to Update UserInfo
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "user/update")
	public ResponseEntity<Object> updateUserInfo(@RequestBody RequestWrapper request ){
		ResponseWrapper responseWrapper = new ResponseWrapper();
		String response = null;
		try {
			LOGGER.info("Incoming Request:"+request.toString());
//			System.out.println("Request"+request.toString());
			JSONObject requestObject = new JSONObject(request);
			JSONObject contextObject = new JSONObject(requestObject.get("context").toString());
			JSONObject requestBodyObject=new JSONObject(requestObject.get("requestBody").toString());
			String userId=contextObject.getString("userId");
			String updateType=contextObject.getString("updateType");
			
			if(updateType.equals("phoneNo")) {
				response = userService.updateUserPhoneNo(userId,requestBodyObject.getString("newValue"));
//				System.out.println("Reponse"+response);
			}else if(updateType.equals("email")) {
				response = userService.updateUserEmail(userId,requestBodyObject.getString("newValue"));
			}else if(updateType.equals("password")) {
				response = userService.updateUserPassword(userId,requestBodyObject.getString("newValue"));
			}
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		}
		catch(BussinessException e) {
			LOGGER.info("BussinessException:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		}catch(Exception e) {
			LOGGER.info("Exception:"+e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		}
		
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}
	
	
//	// EndPoint for Update User PhoneNo
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@PostMapping(value = "user/update/phoneno")
//	public ResponseEntity<Object> updatePhoneNo(@RequestBody User user) {
//		ResponseWrapper responseWrapper = new ResponseWrapper();
//		try {
//			String response = userService.updateUserPhoneNo(user);
//			responseWrapper.setResponseBody(response);
//			responseWrapper.setResponseStatus(Constants.OK);
//		} catch (Exception e) {
//			responseWrapper.setResponseBody(e.getMessage());
//			responseWrapper.setResponseStatus(Constants.USER_NOT_FOUND);
//		}
//		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
//				responseWrapper.getResponseStatus());
//	}
//
//	// EndPoint for Update User Email
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@PostMapping(value = "user/update/email")
//	public ResponseEntity<Object> updateEmail(@RequestBody User user) {
//		ResponseWrapper responseWrapper = new ResponseWrapper();
//		try {
//			String response = userService.updateUserEmail(user);
//			responseWrapper.setResponseBody(response);
//			responseWrapper.setResponseStatus(Constants.OK);
//		} catch (Exception e) {
//			responseWrapper.setResponseBody(e.getMessage());
//			responseWrapper.setResponseStatus(Constants.USER_NOT_FOUND);
//		}
//		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
//				responseWrapper.getResponseStatus());
//	}
//
//	// EndPoint for Update User Password
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@PostMapping(value = "user/update/password")
//	public ResponseEntity<Object> updatePassword(@RequestBody User user) {
//		ResponseWrapper responseWrapper = new ResponseWrapper();
//		try {
//			String response = userService.updateUserPassword(user);
//			responseWrapper.setResponseBody(response);
//			responseWrapper.setResponseStatus(Constants.OK);
//		} catch (Exception e) {
//			responseWrapper.setResponseBody(e.getMessage());
//			responseWrapper.setResponseStatus(Constants.USER_NOT_FOUND);
//		}
//		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
//				responseWrapper.getResponseStatus());
//	}

}
