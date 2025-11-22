package com.mykart.seller.controller;

import com.mykart.seller.dto.ApiResponse;
import com.mykart.seller.dto.Seller;
import com.mykart.seller.service.SellerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller")
public class SellerController {
	
	private static final Logger LOGGER = LogManager.getLogger(SellerController.class);
	
	@Autowired
	SellerService sellerService;
	
    // Register
	@PostMapping("/addSeller")
	public ResponseEntity<ApiResponse<String>> addSeller(@RequestBody Seller seller) throws Exception {
        LOGGER.info("Incoming Request to add seller: " + seller.getSellerName());
        String response = sellerService.addSeller(seller);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.CREATED);
	}

    // Login
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginSeller(@RequestBody Seller loginRequest) throws Exception {
        LOGGER.info("Incoming login request for seller: " + loginRequest.getSellerEmail());
        String token = sellerService.login(loginRequest.getSellerEmail(), loginRequest.getSellerPassword());
        return new ResponseEntity<>(new ApiResponse<>(token), HttpStatus.OK);
    }
	
    // Get Seller
	@GetMapping("/get/{sellerId}")
	public ResponseEntity<ApiResponse<Seller>> getSeller(@PathVariable String sellerId) throws Exception {
        LOGGER.info("Incoming Request to get seller with ID: " + sellerId);
		Seller seller = sellerService.getSeller(sellerId);
		return new ResponseEntity<>(new ApiResponse<>(seller), HttpStatus.OK);
	}

    // Get All
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Seller>>> getAllSellers() throws Exception {
        LOGGER.info("Incoming Request to get all sellers");
        List<Seller> sellers = sellerService.getAllSellers();
        return new ResponseEntity<>(new ApiResponse<>(sellers), HttpStatus.OK);
    }

    // Update Specific Details (e.g., phone, email, password)
    @PostMapping("/update/{sellerId}")
    public ResponseEntity<ApiResponse<String>> updateSeller(@PathVariable String sellerId, @RequestBody Map<String, String> updateRequest) throws Exception {
        LOGGER.info("Incoming Request to update seller: " + sellerId);
        String updateType = updateRequest.get("updateType"); // "phoneNo", "email", "password", "name"
        String newValue = updateRequest.get("newValue");
        String response = sellerService.updateSellerDetails(sellerId, updateType, newValue);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/delete/{sellerId}")
    public ResponseEntity<ApiResponse<String>> deleteSeller(@PathVariable String sellerId) throws Exception {
        LOGGER.info("Request to delete seller: " + sellerId);
        String response = sellerService.deleteSeller(sellerId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }
}