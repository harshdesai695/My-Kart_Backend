package com.myKart.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.myKart.user.dto.User;

public interface UserRepository extends MongoRepository<User,String>{
	User findByUserId(String userId);
	User findByUserName(String userName);
	User findByUserNameAndPassword(String userName,String password);
	void deleteUserByUserId(String userId);
}
