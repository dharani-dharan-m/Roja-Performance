package com.tyreshop.Roja.Performance.repository;

import com.tyreshop.Roja.Performance.entity.Tyre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * TyreRepository - Data Access Object for Tyre entity
 * Provides database operations for Tyre
 * 
 * @author Roja Performance
 * @version 1.0
 */
@Repository
public interface TyreRepository extends JpaRepository<Tyre, Long> {

    /**
     * Find tyre by brand
     * @param brand - Brand name
     * @return List of tyres with given brand
     */
    List<Tyre> findByBrand(String brand);

    /**
     * Find tyre by size
     * @param size - Tyre size
     * @return List of tyres with given size
     */
    List<Tyre> findBySize(String size);

    /**
     * Find tyre by brand and size
     * @param brand - Brand name
     * @param size - Tyre size
     * @return Optional containing tyre if found
     */
    Optional<Tyre> findByBrandAndSize(String brand, String size);

    /**
     * Check if tyre exists with given brand and size
     * @param brand - Brand name
     * @param size - Tyre size
     * @return true if tyre exists, false otherwise
     */
    boolean existsByBrandAndSize(String brand, String size);

    /**
     * Find all tyres with stock quantity greater than zero
     * @return List of available tyres
     */
    @Query("SELECT t FROM Tyre t WHERE t.stockQuantity > 0")
    List<Tyre> findAllAvailable();

    /**
     * Find tyres by price range
     * @param minPrice - Minimum price
     * @param maxPrice - Maximum price
     * @return List of tyres within price range
     */
    @Query("SELECT t FROM Tyre t WHERE t.price BETWEEN :minPrice AND :maxPrice")
    List<Tyre> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
}
