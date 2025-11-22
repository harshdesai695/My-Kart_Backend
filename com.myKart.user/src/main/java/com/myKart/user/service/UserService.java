package com.myKart.user.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myKart.infra.exception.BussinessException;
import com.myKart.user.dto.User;
import com.myKart.user.repository.UserRepository;
import com.myKart.user.util.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;

    // Add New User
    public String addUser(User newUser) throws Exception {
        try {
            // Check if user already exists
            if (userRepository.findByUserName(newUser.getUserName()) != null) {
                throw new BussinessException("Username already exists. Please choose a different one.");
            }
            newUser.setCreatedAt(new Date().toString());
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
        } catch (BussinessException e) {
            throw e; // Re-throw business exceptions to be handled by the global handler
        } catch (Exception e) {
            // For any other exception, throw a generic one
            throw new Exception("Error while adding the user: " + e.getMessage());
        }
        return "User Added Successfully";
    }
    
    public String login(String userName, String password) throws Exception { // Change return type to String
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                throw new BussinessException("User Not Found");
            }
            
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BussinessException("Invalid username or password");
            }
            return jwtUtil.generateToken(user);

        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("An error occurred during login: " + e.getMessage());
        }
    }
    
    
    
//    // Get User Details for Login
//    public User login(String userName, String password) throws Exception {
//        try {
//            User user = userRepository.findByUserName(userName);
//            if (user == null) {
//                throw new BussinessException("User Not Found");
//            }
//            // Use the password encoder to check if the passwords match
//            if (!passwordEncoder.matches(password, user.getPassword())) {
//                throw new BussinessException("Invalid username or password");
//            }
//            return user;
//        } catch (BussinessException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new Exception("An error occurred during login: " + e.getMessage());
//        }
//    }

    // Get User By ID
    @Cacheable(value = "getUserById", key = "#userId")
    public User getUserById(String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new BussinessException("User Not Found");
            }
            return user;
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while retrieving user: " + e.getMessage());
        }
    }

    // Delete User
    @Transactional
    public String deleteUser(String userId) throws Exception {
        try {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new BussinessException("User Not Found");
            }
            userRepository.deleteUserByUserId(userId);
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while deleting user: " + e.getMessage());
        }
        return "User Deleted Successfully";
    }

    // Update User phoneNo
    @Transactional
    public String updateUserPhoneNo(String userId, String newPhoneNo) throws Exception {
        try {
            User user = getUserById(userId); // This will throw "User Not Found" if not exists
            if (user.getPhoneNo().equals(newPhoneNo)) {
                throw new BussinessException("New phone number cannot be the same as the old one.");
            }
            updateUserField(userId, "phoneNo", newPhoneNo);
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error updating phone number: " + e.getMessage());
        }
        return "Phone Number Updated Successfully";
    }

    // Update User Email
    @Transactional
    public String updateUserEmail(String userId, String newEmail) throws Exception {
        try {
            User user = getUserById(userId);
            if (user.getEmail().equals(newEmail)) {
                throw new BussinessException("New email cannot be the same as the old one.");
            }
            updateUserField(userId, "email", newEmail);
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error updating email: " + e.getMessage());
        }
        return "Email Updated Successfully";
    }

    // Update User Password
    @Transactional
    public String updateUserPassword(String userId, String newPassword) throws Exception {
        try {
            User user = getUserById(userId);
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new BussinessException("New password cannot be the same as the old one.");
            }
            // Encrypt the new password before updating
            String encryptedPassword = passwordEncoder.encode(newPassword);
            updateUserField(userId, "password", encryptedPassword);
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error updating password: " + e.getMessage());
        }
        return "Password Updated Successfully";
    }
    
    /**
     * Private helper method to update a single field for a user in MongoDB.
     * @param userId The ID of the user to update.
     * @param fieldName The name of the field to update.
     * @param newValue The new value for the field.
     */
    private void updateUserField(String userId, String fieldName, Object newValue) {
        Query query = new Query(Criteria.where("_id").is(userId));
        Update update = new Update().set(fieldName, newValue);
        mongoTemplate.updateFirst(query, update, User.class);
    }
}