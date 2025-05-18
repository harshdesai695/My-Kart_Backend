package com.myKart.config.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myKart.config.dto.ConfigDTO;
import com.myKart.config.repo.ConfigRepository;

@Service
public class ConfigService {
	@Autowired
    private ConfigRepository configRepository;

    public Optional<ConfigDTO> getConfigByService(String service) {
        return configRepository.findByService(service);
    }
}
