package com.myKart.user.address.controller;

import com.myKart.user.address.service.UserAddressService;
import com.myKart.user.dto.ApiResponse;
import com.myKart.user.dto.UserAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/address")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    private static final Logger LOGGER = LogManager.getLogger(UserAddressController.class);

    @PostMapping("/add/{userId}")
    public ResponseEntity<ApiResponse<String>> addAddress(@RequestBody UserAddress address,@PathVariable String userId) throws Exception {
        LOGGER.info("Incoming request to add address for user: " + userId);
        String response = userAddressService.addAddress(address,userId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<List<UserAddress>>> getAddressesByUserId(@PathVariable String userId) throws Exception {
        LOGGER.info("Incoming request to get addresses for user: " + userId);
        List<UserAddress> addresses = userAddressService.getAddressesByUserId(userId);
        return new ResponseEntity<>(new ApiResponse<>(addresses), HttpStatus.OK);
    }

    @PostMapping("/update/{addressId}")
    public ResponseEntity<ApiResponse<String>> updateAddress(@PathVariable String addressId, @RequestBody UserAddress address) throws Exception {
        LOGGER.info("Incoming request to update address: " + addressId);
        String response = userAddressService.updateAddress(addressId, address);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<ApiResponse<String>> deleteAddress(@PathVariable String addressId) throws Exception {
        LOGGER.info("Incoming request to delete address: " + addressId);
        String response = userAddressService.deleteAddress(addressId);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }
}