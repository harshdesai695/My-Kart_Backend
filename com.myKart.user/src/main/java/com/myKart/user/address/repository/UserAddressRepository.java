package com.myKart.user.address.repository;

import com.myKart.user.dto.UserAddress;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserAddressRepository extends MongoRepository<UserAddress, String> {

    List<UserAddress> findByUserId(String userId);

    void deleteByAddressId(String addressId);
}