package com.myKart.userActivity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class UserActivityApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		final Logger LOGGER = LogManager.getLogger(UserActivityApplication.class);
		LOGGER.info("UserActivityApplication Starts");
		SpringApplication.run(UserActivityApplication.class, args);
	}

}
