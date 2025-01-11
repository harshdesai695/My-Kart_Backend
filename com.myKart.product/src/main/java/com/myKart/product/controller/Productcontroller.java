package com.myKart.product.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BsonBinary;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myKart.infra.constant.Constants;
import com.myKart.infra.dto.RequestWrapper;
import com.myKart.infra.dto.ResponseWrapper;
import com.myKart.infra.exception.BussinessException;
import com.myKart.product.dto.Product;
import com.myKart.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class Productcontroller {

	@Autowired
	ProductService productService;

	private static final Logger LOGGER = LogManager.getLogger(Productcontroller.class);
	
	
	//Getting All Stored Products
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/getAll")
	public ResponseEntity<Object> getAllProduct() {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			List<Product> response=productService.getRandomProduct();
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		}catch (BussinessException e) {
			LOGGER.info("BussinessException:" + e.getMessage());
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}
	
	
	// Post adding new Product
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "/addProduct")
	public ResponseEntity<Object> addProduct(@RequestBody RequestWrapper request) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			LOGGER.info("Incoming Request:" + request.toString());
			JSONObject requestObject = new JSONObject(request);
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody=requestObject.get("requestBody").toString();
			Product product = objectMapper.readValue(requestBody, Product.class);
			String response=productService.addProduct(product);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		} catch (Exception e) {
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}

	// Get EndPoint for Getting Product Data
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/getProduct/{productId}")
	public ResponseEntity<Object> getProduct(@PathVariable String productId) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			LOGGER.info("Get Call for Product" + productId);
			Product response = productService.getProduct(productId);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
			LOGGER.info("Responce For "+productId+" "+responseWrapper.toString());
		} catch (BussinessException e) {
			LOGGER.error("BussinessException", productId,responseWrapper, e);
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		} catch (Exception e) {
			LOGGER.error("Error",productId, responseWrapper, e);
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
				responseWrapper.getResponseStatus());
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<Object> addProduct(
//	        @RequestPart("requestBody") String requestBody,
//	        @RequestPart(value = "file", required = false) MultipartFile file) {
//	    ResponseWrapper responseWrapper = new ResponseWrapper();
//	    try {
//	        LOGGER.info("Incoming Request Body: " + requestBody);
//
//	        // Parse the JSON request body to Product object
//	        ObjectMapper objectMapper = new ObjectMapper();
//	        Product product = objectMapper.readValue(requestBody, Product.class);
//
//	        // Process the file (if provided)
//	        if (file != null) {
//	        	LOGGER.info("Incoming File: " + file.getOriginalFilename());
//	            
//	            // Convert the file to BSON Binary format
//	            BsonBinary bsonBinary = new BsonBinary(file.getBytes());
//	            System.out.println("Boson:"+bsonBinary);
//	            product.getProductImageUrl().add(bsonBinary.getData().toString());
//	        }
//
//	        // Call the service to save the product
//	        String response = productService.addProduct(product);
//	        
//	        // Prepare the response
//	        responseWrapper.setResponseBody(response);
//	        responseWrapper.setResponseStatus(Constants.OK);
//	    } catch (Exception e) {
//	        LOGGER.error("Error processing request", e);
//	        responseWrapper.setResponseBody(e.getMessage());
//	        responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
//	    }
//	    return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
//	            responseWrapper.getResponseStatus());
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/getProductByBrand/{brandName}")
	public ResponseEntity<Object> getProductByBrand(@PathVariable String brandName){
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			LOGGER.info("Get Call for Product By Brand" + brandName);
			List<Product> response=productService.getProductByBrandName(brandName);
			responseWrapper.setResponseBody(response);
			responseWrapper.setResponseStatus(Constants.OK);
		}catch (BussinessException e) {
			LOGGER.error("BussinessException", brandName,responseWrapper, e);
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.BUSSINESS_ERROR);
		} catch (Exception e) {
			LOGGER.error("Error",brandName, responseWrapper, e);
			responseWrapper.setResponseBody(e.getMessage());
			responseWrapper.setResponseStatus(Constants.GENERIC_ERROR);
		}
		return new ResponseEntity(responseWrapper.getResponseBody(), responseWrapper.getHttpHeaders(),
	            responseWrapper.getResponseStatus());
	}
	
	
	
	
}
