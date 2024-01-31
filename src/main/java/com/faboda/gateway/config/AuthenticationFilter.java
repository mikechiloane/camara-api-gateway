package com.faboda.gateway.config;

import com.faboda.gateway.feign.AuthClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Configuration
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final int FILTER_ORDER = -1;
    private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);
    private final AuthClient authClient;

    public AuthenticationFilter(@Lazy AuthClient authClient) {
        this.authClient = authClient;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        var headers = request.getHeaders();
          if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.error("No authorization header");
                return chain.filter(exchange);
            }
        String token = Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)).replace("Bearer ", "");
        exchange.getRequest().mutate().header("X-User-Email", getUserEmailFromToken(token));
        return chain.filter(exchange);
    }

    public String getUserEmailFromToken(String token) {
        return authClient.getUserEmailFromToken(token).getEmail();
    }

    @Override
    public int getOrder() {
        return FILTER_ORDER;
    }
}
