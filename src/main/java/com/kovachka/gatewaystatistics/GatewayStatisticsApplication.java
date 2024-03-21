package com.kovachka.gatewaystatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GatewayStatisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayStatisticsApplication.class, args);
	}
}
