package com.tyreshop.Roja.Performance.controller;

import com.tyreshop.Roja.Performance.dto.ApiResponse;
import com.tyreshop.Roja.Performance.dto.TyreRequestDTO;
import com.tyreshop.Roja.Performance.dto.TyreResponseDTO;
import com.tyreshop.Roja.Performance.service.TyreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TyreController - REST Controller for Tyre operations
 * Handles all HTTP requests related to tyres
 * 
 * @author Roja Performance
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tyres")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class TyreController {

    private final TyreService tyreService;

    /**
     * Create a new tyre
     * POST /api/v1/tyres
     * 
     * @param tyreRequestDTO - Tyre data
     * @return Created tyre with 201 status
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TyreResponseDTO>> createTyre(
            @Valid @RequestBody TyreRequestDTO tyreRequestDTO) {
        log.info("CREATE Tyre - Brand: {}, Size: {}", tyreRequestDTO.getBrand(), tyreRequestDTO.getSize());
        
        TyreResponseDTO createdTyre = tyreService.createTyre(tyreRequestDTO);
        
        ApiResponse<TyreResponseDTO> response = ApiResponse.<TyreResponseDTO>builder()
                .success(true)
                .message("Tyre created successfully")
                .data(createdTyre)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get tyre by ID
     * GET /api/v1/tyres/{id}
     * 
     * @param id - Tyre ID
     * @return Tyre details with 200 status
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TyreResponseDTO>> getTyreById(@PathVariable Long id) {
        log.info("GET Tyre - ID: {}", id);
        
        TyreResponseDTO tyre = tyreService.getTyreById(id);
        
        ApiResponse<TyreResponseDTO> response = ApiResponse.<TyreResponseDTO>builder()
                .success(true)
                .message("Tyre retrieved successfully")
                .data(tyre)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get all tyres
     * GET /api/v1/tyres
     * 
     * @return List of all tyres with 200 status
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TyreResponseDTO>>> getAllTyres() {
        log.info("GET All Tyres");
        
        List<TyreResponseDTO> tyres = tyreService.getAllTyres();
        
        ApiResponse<List<TyreResponseDTO>> response = ApiResponse.<List<TyreResponseDTO>>builder()
                .success(true)
                .message("Tyres retrieved successfully, count: " + tyres.size())
                .data(tyres)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get all available tyres (with stock > 0)
     * GET /api/v1/tyres/available/list
     * 
     * @return List of available tyres with 200 status
     */
    @GetMapping("/available/list")
    public ResponseEntity<ApiResponse<List<TyreResponseDTO>>> getAvailableTyres() {
        log.info("GET Available Tyres");
        
        List<TyreResponseDTO> tyres = tyreService.getAllAvailableTyres();
        
        ApiResponse<List<TyreResponseDTO>> response = ApiResponse.<List<TyreResponseDTO>>builder()
                .success(true)
                .message("Available tyres retrieved successfully, count: " + tyres.size())
                .data(tyres)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get tyres by brand
     * GET /api/v1/tyres/brand/{brand}
     * 
     * @param brand - Brand name
     * @return List of tyres with given brand
     */
    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse<List<TyreResponseDTO>>> getTyresByBrand(@PathVariable String brand) {
        log.info("GET Tyres by Brand: {}", brand);
        
        List<TyreResponseDTO> tyres = tyreService.getTyresByBrand(brand);
        
        ApiResponse<List<TyreResponseDTO>> response = ApiResponse.<List<TyreResponseDTO>>builder()
                .success(true)
                .message("Tyres with brand '" + brand + "' retrieved successfully, count: " + tyres.size())
                .data(tyres)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get tyres by size
     * GET /api/v1/tyres/size/{size}
     * 
     * @param size - Tyre size
     * @return List of tyres with given size
     */
    @GetMapping("/size/{size}")
    public ResponseEntity<ApiResponse<List<TyreResponseDTO>>> getTyresBySize(@PathVariable String size) {
        log.info("GET Tyres by Size: {}", size);
        
        List<TyreResponseDTO> tyres = tyreService.getTyresBySize(size);
        
        ApiResponse<List<TyreResponseDTO>> response = ApiResponse.<List<TyreResponseDTO>>builder()
                .success(true)
                .message("Tyres with size '" + size + "' retrieved successfully, count: " + tyres.size())
                .data(tyres)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Get tyres by price range
     * GET /api/v1/tyres/price?minPrice=1000&maxPrice=5000
     * 
     * @param minPrice - Minimum price
     * @param maxPrice - Maximum price
     * @return List of tyres within price range
     */
    @GetMapping("/price")
    public ResponseEntity<ApiResponse<List<TyreResponseDTO>>> getTyresByPriceRange(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        log.info("GET Tyres by Price Range: {} - {}", minPrice, maxPrice);
        
        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = Double.MAX_VALUE;
        
        List<TyreResponseDTO> tyres = tyreService.getTyresByPriceRange(minPrice, maxPrice);
        
        ApiResponse<List<TyreResponseDTO>> response = ApiResponse.<List<TyreResponseDTO>>builder()
                .success(true)
                .message("Tyres with price range " + minPrice + " - " + maxPrice + " retrieved successfully, count: " + tyres.size())
                .data(tyres)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Update tyre
     * PUT /api/v1/tyres/{id}
     * 
     * @param id - Tyre ID
     * @param tyreRequestDTO - Updated tyre data
     * @return Updated tyre with 200 status
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TyreResponseDTO>> updateTyre(
            @PathVariable Long id,
            @Valid @RequestBody TyreRequestDTO tyreRequestDTO) {
        log.info("UPDATE Tyre - ID: {}", id);
        
        TyreResponseDTO updatedTyre = tyreService.updateTyre(id, tyreRequestDTO);
        
        ApiResponse<TyreResponseDTO> response = ApiResponse.<TyreResponseDTO>builder()
                .success(true)
                .message("Tyre updated successfully")
                .data(updatedTyre)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Delete tyre
     * DELETE /api/v1/tyres/{id}
     * 
     * @param id - Tyre ID
     * @return 204 No Content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTyre(@PathVariable Long id) {
        log.info("DELETE Tyre - ID: {}", id);
        
        tyreService.deleteTyre(id);
        
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Tyre deleted successfully")
                .timestamp(System.currentTimeMillis())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    /**
     * Update stock quantity
     * PATCH /api/v1/tyres/{id}/stock
     * 
     * @param id - Tyre ID
     * @param quantity - Quantity to add/subtract
     * @return Updated tyre with 200 status
     */
    @PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<TyreResponseDTO>> updateStockQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        log.info("UPDATE Stock - Tyre ID: {}, Quantity: {}", id, quantity);
        
        TyreResponseDTO updatedTyre = tyreService.updateStockQuantity(id, quantity);
        
        ApiResponse<TyreResponseDTO> response = ApiResponse.<TyreResponseDTO>builder()
                .success(true)
                .message("Stock quantity updated successfully")
                .data(updatedTyre)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Reduce stock quantity (for orders)
     * POST /api/v1/tyres/{id}/reduce-stock
     * 
     * @param id - Tyre ID
     * @param quantity - Quantity to reduce
     * @return Updated tyre with 200 status
     */
    @PostMapping("/{id}/reduce-stock")
    public ResponseEntity<ApiResponse<TyreResponseDTO>> reduceStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        log.info("REDUCE Stock - Tyre ID: {}, Quantity: {}", id, quantity);
        
        TyreResponseDTO updatedTyre = tyreService.reduceStock(id, quantity);
        
        ApiResponse<TyreResponseDTO> response = ApiResponse.<TyreResponseDTO>builder()
                .success(true)
                .message("Stock quantity reduced successfully")
                .data(updatedTyre)
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     * GET /api/v1/tyres/health/status
     * 
     * @return API status
     */
    @GetMapping("/health/status")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        log.info("Health Check - Tyre API");
        
        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Tyre API is running")
                .data("OK")
                .timestamp(System.currentTimeMillis())
                .build();
        
        return ResponseEntity.ok(response);
    }
}
