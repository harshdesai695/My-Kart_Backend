package com.myKart.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		
		final Logger LOGGER = LogManager.getLogger(UserApplication.class);
		SpringApplication app = new SpringApplication(UserApplication.class);
        app.addInitializers(new RemoteConfigInitializer());
        LOGGER.info("UserApplication Config Loded");
        app.run(args);
		
		LOGGER.info("UserApplication Starts");
//		SpringApplication.run(UserApplication.class, args);
	}

}
