package com.tyreshop.Roja.Performance.config;

import com.tyreshop.Roja.Performance.entity.Tyre;
import com.tyreshop.Roja.Performance.repository.TyreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataInitializer - Initialize sample data for the application
 * Phase 1: Initializes sample tyre data
 * 
 * @author Roja Performance
 * @version 1.0
 */
@Slf4j
@Configuration
public class DataInitializer {

    /**
     * Initialize sample tyre data on application startup
     */
    @Bean
    public CommandLineRunner initTyreData(TyreRepository tyreRepository) {
        return args -> {
            if (tyreRepository.count() == 0) {
                log.info("Initializing sample tyre data...");

                // Michelin tyres
                tyreRepository.save(Tyre.builder()
                        .brand("Michelin")
                        .size("195/65R15")
                        .price(4500.00)
                        .stockQuantity(50)
                        .description("All-season comfort tyre, excellent grip and durability")
                        .build());

                tyreRepository.save(Tyre.builder()
                        .brand("Michelin")
                        .size("215/60R16")
                        .price(5200.00)
                        .stockQuantity(45)
                        .description("Premium all-season tyre for family vehicles")
                        .build());

                // Bridgestone tyres
                tyreRepository.save(Tyre.builder()
                        .brand("Bridgestone")
                        .size("195/65R15")
                        .price(4200.00)
                        .stockQuantity(60)
                        .description("Reliable and affordable all-season tyre")
                        .build());

                tyreRepository.save(Tyre.builder()
                        .brand("Bridgestone")
                        .size("225/45R17")
                        .price(6000.00)
                        .stockQuantity(30)
                        .description("Premium performance tyre for sports vehicles")
                        .build());

                // Continental tyres
                tyreRepository.save(Tyre.builder()
                        .brand("Continental")
                        .size("205/55R16")
                        .price(5500.00)
                        .stockQuantity(40)
                        .description("Eco-friendly tyre with low rolling resistance")
                        .build());

                tyreRepository.save(Tyre.builder()
                        .brand("Continental")
                        .size("215/60R16")
                        .price(5800.00)
                        .stockQuantity(35)
                        .description("Premium comfort tyre for executive vehicles")
                        .build());

                // Goodyear tyres
                tyreRepository.save(Tyre.builder()
                        .brand("Goodyear")
                        .size("195/65R15")
                        .price(4000.00)
                        .stockQuantity(70)
                        .description("Budget-friendly all-season tyre")
                        .build());

                tyreRepository.save(Tyre.builder()
                        .brand("Goodyear")
                        .size("235/40R18")
                        .price(7000.00)
                        .stockQuantity(25)
                        .description("High-performance tyre for premium vehicles")
                        .build());

                // Yokohama tyres
                tyreRepository.save(Tyre.builder()
                        .brand("Yokohama")
                        .size("205/55R16")
                        .price(5000.00)
                        .stockQuantity(50)
                        .description("Comfortable tyre with excellent handling")
                        .build());

                tyreRepository.save(Tyre.builder()
                        .brand("Yokohama")
                        .size("225/45R17")
                        .price(6400.00)
                        .stockQuantity(28)
                        .description("Sports performance tyre with superior grip")
                        .build());

                log.info("Sample tyre data initialized successfully. Total tyres: {}", tyreRepository.count());
            }
        };
    }
}

