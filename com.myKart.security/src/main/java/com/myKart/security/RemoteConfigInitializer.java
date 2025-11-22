package com.myKart.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
// Import the reactive WebClient
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Map;

public class RemoteConfigInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    final Logger LOGGER = LogManager.getLogger(RemoteConfigInitializer.class);
    private static final String CONFIG_SERVICE_URL = "http://localhost:8001/config/Security";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        
        // 1. Create a reactive WebClient instead of RestTemplate
        WebClient webClient = WebClient.create();

        try {
            // 2. Call the config service using the reactive client
            // We use .block() here because initialization is a blocking, one-time event.
            Map<String, Object> response = webClient.get()
                    .uri(CONFIG_SERVICE_URL)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

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