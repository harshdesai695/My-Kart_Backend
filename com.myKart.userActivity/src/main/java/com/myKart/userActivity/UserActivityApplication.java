package com.myKart.userActivity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class UserActivityApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		final Logger LOGGER = LogManager.getLogger(UserActivityApplication.class);
		SpringApplication app = new SpringApplication(UserActivityApplication.class);
		app.addInitializers(new RemoteConfigInitializer());
		app.run(args);
		LOGGER.info("UserActivityApplication Starts");
//		SpringApplication.run(UserActivityApplication.class, args);
	}

}
