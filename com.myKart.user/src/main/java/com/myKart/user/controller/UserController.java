package com.myKart.user.controller;



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
import com.myKart.infra.exception.BussinessException;
import com.myKart.user.dto.ApiResponse;
import com.myKart.user.dto.User;
import com.myKart.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	/**
     * Endpoint to add a new user.
     * @param user The user details from the request body.
     * @return A response entity with the result of the operation.
     */
    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse<String>> addUser(@RequestBody User user) throws Exception {
        LOGGER.info("Incoming request to add user: " + user.getUserName());
        String response = userService.addUser(user);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.CREATED);
    }

    /**
     * Endpoint for user login.
     * @param loginRequest A DTO containing the username and password.
     * @return A response entity with the logged-in user's details.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody User loginRequest) throws Exception {
        LOGGER.info("Incoming login request for user: " + loginRequest.getUserName());
        
        // This now returns a JWT string
        String token = userService.login(loginRequest.getUserName(), loginRequest.getPassword()); 
        
        // Return the token in the ApiResponse
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK); 
    }
    
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<User>> loginUser(@RequestBody User loginRequest) throws Exception {
//        LOGGER.info("Incoming login request for user: " + loginRequest.getUserName());
//        User loggedInUser = userService.login(loginRequest.getUserName(), loginRequest.getPassword());
//        return new ResponseEntity<>(new ApiResponse<>(loggedInUser), HttpStatus.OK);
//    }

    /**
     * Endpoint to get user details by user ID.
     * @param userId The ID of the user to retrieve.
     * @return A response entity with the user's details.
     */
    @GetMapping("/getUser/{userId}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable String userId) throws Exception {
        LOGGER.info("Get call to retrieve user data for userId: " + userId);
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(new ApiResponse<>(user), HttpStatus.OK);
    }

    /**
     * Endpoint to delete a user by user ID.
     * @param userId The ID of the user to delete.
     * @return A response entity with the result of the operation.
     */
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String userId) throws Exception {
        LOGGER.info("Get call to delete user data for userId: " + userId);
        String response = userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
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
