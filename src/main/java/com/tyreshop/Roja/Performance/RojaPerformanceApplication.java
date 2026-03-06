package com.tyreshop.Roja.Performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Roja Performance Application - Main entry point
 * Spring Boot application for Tyre Shop Management System
 * 
 * @author Roja Performance
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.tyreshop.Roja.Performance")
public class RojaPerformanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RojaPerformanceApplication.class, args);
	}
}
