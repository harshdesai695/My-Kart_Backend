package com.myKart.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;



@SpringBootApplication
@EnableDiscoveryClient
public class SecurityApplication{
	
	final static Logger LOGGER = LogManager.getLogger(SecurityApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(SecurityApplication.class);
		app.addInitializers(new RemoteConfigInitializer());
        LOGGER.info("SecurityApplication Config Loded");
        app.run(args);
		LOGGER.info("SecurityApplication Application Starts");
	}

}
