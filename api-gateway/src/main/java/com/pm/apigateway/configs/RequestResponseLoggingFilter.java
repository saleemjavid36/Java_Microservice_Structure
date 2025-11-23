package com.pm.apigateway.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestResponseLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public int getOrder() {
        return -1; // run first
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Use getMethodValue() to reliably get the string representation of the HTTP Method
        String method = String.valueOf(request.getMethod());

        log.info("➡️ Incoming Request: {} {}", method, request.getURI());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();

            // Safely get the status code as a String. Use getStatusCode() if available, 
            // otherwise log 'N/A' or default to 500 in case of failure before response creation.
            String status = response.getStatusCode() != null
                    ? response.getStatusCode().toString()
                    : "N/A (Error before response)";

            log.info("⬅️ Outgoing Response: Status={}, URI={}", status, request.getURI());
        }));
    }
}