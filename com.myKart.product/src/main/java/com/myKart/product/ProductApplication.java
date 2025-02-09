package com.myKart.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class ProductApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		final Logger LOGGER = LogManager.getLogger(ProductApplication.class);
		LOGGER.info("Product Application Starts");
		SpringApplication.run(ProductApplication.class, args);
	}
}
