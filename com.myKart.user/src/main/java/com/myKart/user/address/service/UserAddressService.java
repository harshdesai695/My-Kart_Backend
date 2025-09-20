package com.myKart.user.address.service;

import com.myKart.infra.exception.BussinessException;
import com.myKart.user.address.repository.UserAddressRepository;
import com.myKart.user.dto.UserAddress;
import com.myKart.user.repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    /**
     * Adds a new address for a user, but first verifies that the user exists.
     * @param newAddress The new address to add.
     * @return A success message.
     * @throws Exception if the user does not exist or if there's an error during saving.
     */
    public String addAddress(UserAddress newAddress,String userId) throws Exception {
        try {
            // Check if a user with the given userId exists
            if (!userRepository.existsById(userId)) {
                throw new BussinessException("User not found. Cannot add address for a non-existent user.");
            }
            newAddress.setUserId(userId);
            userAddressRepository.save(newAddress);
            
        } catch (BussinessException e) {
            throw e; // Re-throw business exceptions
        } catch (Exception e) {
            throw new Exception("Error while adding the address: " + e.getMessage());
        }
        return "Address Added Successfully";
    }

    // Get all addresses for a user
    public List<UserAddress> getAddressesByUserId(String userId) throws Exception {
        try {
            List<UserAddress> addresses = userAddressRepository.findByUserId(userId);
            if (addresses.isEmpty()) {
                throw new BussinessException("No addresses found for this user.");
            }
            return addresses;
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while retrieving addresses: " + e.getMessage());
        }
    }

    // Update an existing address
    @Transactional
    public String updateAddress(String addressId, UserAddress updatedAddress) throws Exception {
        try {
            Optional<UserAddress> existingAddressOpt = userAddressRepository.findById(addressId);
            if (existingAddressOpt.isEmpty()) {
                throw new BussinessException("Address not found.");
            }
            UserAddress existingAddress = existingAddressOpt.get();
            // Update fields
            existingAddress.setHomeNo(updatedAddress.getHomeNo());
            existingAddress.setStreetLine1(updatedAddress.getStreetLine1());
            existingAddress.setStreetLine1(updatedAddress.getStreetLine2());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setPostalCode(updatedAddress.getPostalCode());
            existingAddress.setCountry(updatedAddress.getCountry());
            userAddressRepository.save(existingAddress);
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while updating address: " + e.getMessage());
        }
        return "Address Updated Successfully";
    }

    // Delete an address
    @Transactional
    public String deleteAddress(String addressId) throws Exception {
        try {
            if (!userAddressRepository.existsById(addressId)) {
                throw new BussinessException("Address not found.");
            }
            userAddressRepository.deleteById(addressId); // Corrected to use deleteById
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while deleting address: " + e.getMessage());
        }
        return "Address Deleted Successfully";
    }
}