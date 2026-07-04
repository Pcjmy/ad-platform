package com.adplatform.adgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdGatewayApplication.class, args);
    }
}
