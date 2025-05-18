package com.myKart.config.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.myKart.config.dto.ConfigDTO;


public interface  ConfigRepository extends MongoRepository<ConfigDTO, String> {
	Optional<ConfigDTO> findByService(String service);
}
