package com.myKart.servicceDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EurekaApplication.class);
		app.addInitializers(new RemoteConfigInitializer());
		app.run(args);
//		SpringApplication.run(EurekaApplication.class, args);
	}

}
