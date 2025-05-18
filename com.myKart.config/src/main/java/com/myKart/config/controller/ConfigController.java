package com.myKart.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myKart.config.service.ConfigService;

@RestController
@RequestMapping("/config")
public class ConfigController {
	
	
	@Autowired
    private ConfigService configService;

    @GetMapping("/{service}")
    public ResponseEntity<?> getConfigByService(@PathVariable String service) {
        return configService.getConfigByService(service)
                .map(configEntry -> ResponseEntity.ok(configEntry))
                .orElse(ResponseEntity.notFound().build());
    }
	
}
