package com.myKart.servicceDiscovery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

public class RemoteConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	final Logger LOGGER = LogManager.getLogger(RemoteConfigInitializer.class);
    private static final String CONFIG_SERVICE_URL = "http://localhost:8001/config/Eureka";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Call your config service API
            Map<String, Object> response = restTemplate.getForObject(CONFIG_SERVICE_URL, Map.class);

            if (response != null && response.containsKey("config")) {
                Map<String, Object> config = (Map<String, Object>) response.get("config");

                ConfigurableEnvironment env = applicationContext.getEnvironment();

                // Add your config as a new property source with high precedence
                env.getPropertySources().addFirst(new MapPropertySource("remoteConfig", config));

                System.out.println("Remote config loaded and added to environment");
                
                MutablePropertySources propertySources = env.getPropertySources();
                propertySources.forEach(ps -> {
                    if (ps instanceof EnumerablePropertySource) {
                        EnumerablePropertySource<?> eps = (EnumerablePropertySource<?>) ps;
                        Arrays.asList(eps.getPropertyNames()).forEach(propertyName -> {
                        	LOGGER.info(propertyName + " = " + env.getProperty(propertyName));
                        });
                    } else {
                        System.out.println("Property Source: " + ps.getName() + " (non-enumerable)");
                    }
                });
                
                
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load remote config, aborting startup", e);
        }
    }
}