package com.myKart.user.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myKart.infra.exception.BussinessException;
import com.myKart.user.dto.User;
import com.myKart.user.repository.UserRepository;

@Service
public class UserService {
	

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	

	Date date = new Date();
	
	// Add New User
	public String addUser(User newUser) throws Exception {
		try {
			newUser.setCreatedAt(date.toString());
			userRepository.save(newUser);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "User Added";
	}
	
	//Get User Details
	public User login(String userName,String password ) throws Exception{
		User user;
		try {
			user=userRepository.findByUserNameAndPassword(userName, password);
			if(user==null) {
				throw new BussinessException("User Not Found");
			}
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return user;
	}
	
	//Get User By ID
	public User getUserById(String userId) throws Exception{
		User user;
		try {
			user=userRepository.findByUserId(userId);
			if(user==null) {
				throw new BussinessException("User Not Found");
			}
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return user;
	}
	
	
	// Delete User
	public String deleteUser(String userId) throws Exception {
		User user;
		try {
			user=userRepository.findByUserId(userId);
			if(user==null) {
				throw new BussinessException("User Not Found");
			}
			userRepository.deleteUserByUserId(user.getUserId());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return "User Deleted";
	}
	
	

	// Update User phoneNo
	@Transactional
	public String updateUserPhoneNo(String userId,String newPhoneNo) throws Exception {
		User user;
		try {
			user=getUserById(userId);
			if(user==null) {
				throw new BussinessException("User Not Found");
			}
			else if(user.getPhoneNo().equals(newPhoneNo)) {
				throw new BussinessException("Old PhoneNo and New PhoneNo cannot be Same");
			}
			Query query = new Query(Criteria.where("_id").is(userId));
			Update update = new Update().set("phoneNo",newPhoneNo );
			mongoTemplate.updateFirst(query, update, User.class);
		} catch (Exception e) {
			System.out.println("[Exception" + e.fillInStackTrace() + "]");
			throw new Exception(e.getMessage());
		}
		return "Phone No Updated";
	}

	// Update User Email
	@Transactional
	public String updateUserEmail(String userId,String newEmail) throws Exception {
		User user;
		try {
			user=getUserById(userId);
			if(user==null) {
				throw new BussinessException("User Not Found");
			}
			else if(user.getEmail().equals(newEmail)) {
				throw new BussinessException("Old Email and New Email cannot be Same");
			}
			Query query = new Query(Criteria.where("_id").is(userId));
			Update update = new Update().set("email", newEmail);
			mongoTemplate.updateFirst(query, update, User.class);
		} catch (Exception e) {
			System.out.println("[Exception" + e.fillInStackTrace() + "]");
			throw new Exception("Email not Updated"+e.getMessage());
		}
		return "Email Updated";
	}
	
	//Update User Password
	@Transactional
	public String updateUserPassword(String userId,String newPassword) throws Exception{
		User user;
		try {
			user=getUserById(userId);
			if(user==null) {
				throw new BussinessException("User Not Found");
			}
			else if(user.getPassword().equals(newPassword)) {
				throw new BussinessException("Old Password and New Password cannot be Same");
			}
			Query query = new Query(Criteria.where("_id").is(userId));
			Update update = new Update().set("password", newPassword);
			mongoTemplate.updateFirst(query, update, User.class);
		} catch (Exception e) {
			System.out.println("[Exception" + e.fillInStackTrace() + "]");
			throw new Exception(e.getMessage());
		}
		return "Password Updated";
	}

}