package com.tyreshop.Roja.Performance.service;

import com.tyreshop.Roja.Performance.dto.TyreRequestDTO;
import com.tyreshop.Roja.Performance.dto.TyreResponseDTO;
import com.tyreshop.Roja.Performance.entity.Tyre;

import java.util.List;

/**
 * TyreService - Business logic layer interface for Tyre operations
 * 
 * @author Roja Performance
 * @version 1.0
 */
public interface TyreService {

    /**
     * Create a new tyre
     * @param tyreRequestDTO - Tyre data from request
     * @return Created tyre response
     */
    TyreResponseDTO createTyre(TyreRequestDTO tyreRequestDTO);

    /**
     * Get tyre by ID
     * @param id - Tyre ID
     * @return Tyre response
     * @throws ResourceNotFoundException if tyre not found
     */
    TyreResponseDTO getTyreById(Long id);

    /**
     * Get all tyres
     * @return List of all tyres
     */
    List<TyreResponseDTO> getAllTyres();

    /**
     * Get all available tyres (with stock > 0)
     * @return List of available tyres
     */
    List<TyreResponseDTO> getAllAvailableTyres();

    /**
     * Get tyres by brand
     * @param brand - Brand name
     * @return List of tyres with given brand
     */
    List<TyreResponseDTO> getTyresByBrand(String brand);

    /**
     * Get tyres by size
     * @param size - Tyre size
     * @return List of tyres with given size
     */
    List<TyreResponseDTO> getTyresBySize(String size);

    /**
     * Get tyres by price range
     * @param minPrice - Minimum price
     * @param maxPrice - Maximum price
     * @return List of tyres within price range
     */
    List<TyreResponseDTO> getTyresByPriceRange(Double minPrice, Double maxPrice);

    /**
     * Update tyre
     * @param id - Tyre ID
     * @param tyreRequestDTO - Updated tyre data
     * @return Updated tyre response
     * @throws ResourceNotFoundException if tyre not found
     */
    TyreResponseDTO updateTyre(Long id, TyreRequestDTO tyreRequestDTO);

    /**
     * Delete tyre
     * @param id - Tyre ID
     * @throws ResourceNotFoundException if tyre not found
     */
    void deleteTyre(Long id);

    /**
     * Update tyre stock quantity
     * @param id - Tyre ID
     * @param quantity - Quantity to add/subtract
     * @return Updated tyre response
     * @throws ResourceNotFoundException if tyre not found
     * @throws IllegalArgumentException if resulting quantity would be negative
     */
    TyreResponseDTO updateStockQuantity(Long id, Integer quantity);

    /**
     * Reduce stock quantity (for orders)
     * @param id - Tyre ID
     * @param quantity - Quantity to reduce
     * @return Updated tyre response
     * @throws ResourceNotFoundException if tyre not found
     * @throws IllegalArgumentException if insufficient stock
     */
    TyreResponseDTO reduceStock(Long id, Integer quantity);

    /**
     * Convert Tyre entity to TyreResponseDTO
     * @param tyre - Tyre entity
     * @return TyreResponseDTO
     */
    TyreResponseDTO convertToResponseDTO(Tyre tyre);

    /**
     * Convert TyreRequestDTO to Tyre entity
     * @param tyreRequestDTO - Request DTO
     * @return Tyre entity
     */
    Tyre convertToEntity(TyreRequestDTO tyreRequestDTO);
}
