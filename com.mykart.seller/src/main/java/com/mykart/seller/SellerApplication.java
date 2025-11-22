package com.mykart.seller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class SellerApplication extends SpringBootServletInitializer {

	public static void main(String[] args)  {
		
		final Logger LOGGER = LogManager.getLogger(SellerApplication.class);
		
		SpringApplication app = new SpringApplication(SellerApplication.class);
		app.addInitializers(new RemoteConfigInitializer());
		LOGGER.info("SellerApplication Config Loded");
		app.run(args);
		LOGGER.info("SellerApplication Application Starts");
	}

}
