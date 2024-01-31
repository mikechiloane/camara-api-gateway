package com.faboda.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class GateConfig {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user", r -> r.path("/v1/user/**")
                        .uri("https://user-service-u1f1.onrender.com"))
                .route("device_location_retrieve", r -> r.path("/location-retrieval/**")
                        .uri("http://localhost:9091"))
                .route("device_location_verify", r -> r.path("/location-verification/**")
                        .uri("http://localhost:9091"))
                .route("messaging", r -> r.path("/v1/message/**")
                        .uri("https://messaging-service-1mwa.onrender.com"))

                .build();
    }


}
