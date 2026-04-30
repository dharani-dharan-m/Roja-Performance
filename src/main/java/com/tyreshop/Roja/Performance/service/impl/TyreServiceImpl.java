package com.tyreshop.Roja.Performance.service.impl;

import com.tyreshop.Roja.Performance.dto.TyreRequestDTO;
import com.tyreshop.Roja.Performance.dto.TyreResponseDTO;
import com.tyreshop.Roja.Performance.entity.Tyre;
import com.tyreshop.Roja.Performance.exception.ResourceNotFoundException;
import com.tyreshop.Roja.Performance.repository.TyreRepository;
import com.tyreshop.Roja.Performance.service.TyreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TyreServiceImpl - Implementation of TyreService business logic
 * 
 * @author Roja Performance
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TyreServiceImpl implements TyreService {

    private final TyreRepository tyreRepository;

    /**
     * Create a new tyre
     */
    @Override
    public TyreResponseDTO createTyre(TyreRequestDTO tyreRequestDTO) {
        log.info("Creating new tyre: brand={}, size={}", tyreRequestDTO.getBrand(), tyreRequestDTO.getSize());
        
        // Check for duplicate
        if (tyreRepository.existsByBrandAndSize(tyreRequestDTO.getBrand(), tyreRequestDTO.getSize())) {
            throw new IllegalArgumentException(
                    String.format("Tyre with brand '%s' and size '%s' already exists",
                            tyreRequestDTO.getBrand(), tyreRequestDTO.getSize()));
        }
        
        Tyre tyre = convertToEntity(tyreRequestDTO);
        Tyre savedTyre = tyreRepository.save(tyre);
        
        log.info("Tyre created successfully with ID: {}", savedTyre.getId());
        return convertToResponseDTO(savedTyre);
    }

    /**
     * Get tyre by ID
     */
    @Override
    @Transactional(readOnly = true)
    public TyreResponseDTO getTyreById(Long id) {
        log.info("Fetching tyre with ID: {}", id);
        
        Tyre tyre = tyreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tyre not found with ID: %d", id)));
        
        return convertToResponseDTO(tyre);
    }

    /**
     * Get all tyres
     */
    @Override
    @Transactional(readOnly = true)
    public List<TyreResponseDTO> getAllTyres() {
        log.info("Fetching all tyres");
        
        return tyreRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all available tyres
     */
    @Override
    @Transactional(readOnly = true)
    public List<TyreResponseDTO> getAllAvailableTyres() {
        log.info("Fetching all available tyres");
        
        return tyreRepository.findAllAvailable()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get tyres by brand
     */
    @Override
    @Transactional(readOnly = true)
    public List<TyreResponseDTO> getTyresByBrand(String brand) {
        log.info("Fetching tyres with brand: {}", brand);
        
        return tyreRepository.findByBrand(brand)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get tyres by size
     */
    @Override
    @Transactional(readOnly = true)
    public List<TyreResponseDTO> getTyresBySize(String size) {
        log.info("Fetching tyres with size: {}", size);
        
        return tyreRepository.findBySize(size)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get tyres by price range
     */
    @Override
    @Transactional(readOnly = true)
    public List<TyreResponseDTO> getTyresByPriceRange(Double minPrice, Double maxPrice) {
        log.info("Fetching tyres with price range: {} - {}", minPrice, maxPrice);
        
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range provided");
        }
        
        return tyreRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Update tyre
     */
    @Override
    public TyreResponseDTO updateTyre(Long id, TyreRequestDTO tyreRequestDTO) {
        log.info("Updating tyre with ID: {}", id);
        
        Tyre tyre = tyreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tyre not found with ID: %d", id)));
        
        // Check for duplicate if brand/size changed
        if (!tyre.getBrand().equals(tyreRequestDTO.getBrand()) || 
            !tyre.getSize().equals(tyreRequestDTO.getSize())) {
            if (tyreRepository.existsByBrandAndSize(tyreRequestDTO.getBrand(), tyreRequestDTO.getSize())) {
                throw new IllegalArgumentException(
                        String.format("Tyre with brand '%s' and size '%s' already exists",
                                tyreRequestDTO.getBrand(), tyreRequestDTO.getSize()));
            }
        }
        
        tyre.setBrand(tyreRequestDTO.getBrand());
        tyre.setSize(tyreRequestDTO.getSize());
        tyre.setPrice(tyreRequestDTO.getPrice());
        tyre.setStockQuantity(tyreRequestDTO.getStockQuantity());
        tyre.setDescription(tyreRequestDTO.getDescription());
        
        Tyre updatedTyre = tyreRepository.save(tyre);
        log.info("Tyre updated successfully with ID: {}", updatedTyre.getId());
        
        return convertToResponseDTO(updatedTyre);
    }

    /**
     * Delete tyre
     */
    @Override
    public void deleteTyre(Long id) {
        log.info("Deleting tyre with ID: {}", id);
        
        if (!tyreRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("Tyre not found with ID: %d", id));
        }
        
        tyreRepository.deleteById(id);
        log.info("Tyre deleted successfully with ID: {}", id);
    }

    /**
     * Update stock quantity
     */
    @Override
    public TyreResponseDTO updateStockQuantity(Long id, Integer quantity) {
        log.info("Updating stock quantity for tyre ID: {} with quantity: {}", id, quantity);
        
        Tyre tyre = tyreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tyre not found with ID: %d", id)));
        
        int newQuantity = tyre.getStockQuantity() + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException(
                    String.format("Invalid stock update: resulting quantity (%d) cannot be negative", newQuantity));
        }
        
        tyre.setStockQuantity(newQuantity);
        Tyre updatedTyre = tyreRepository.save(tyre);
        
        return convertToResponseDTO(updatedTyre);
    }

    /**
     * Reduce stock quantity
     */
    @Override
    public TyreResponseDTO reduceStock(Long id, Integer quantity) {
        log.info("Reducing stock for tyre ID: {} by quantity: {}", id, quantity);
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to reduce must be greater than zero");
        }
        
        Tyre tyre = tyreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Tyre not found with ID: %d", id)));
        
        if (tyre.getStockQuantity() < quantity) {
            throw new IllegalArgumentException(
                    String.format("Insufficient stock. Available: %d, Requested: %d",
                            tyre.getStockQuantity(), quantity));
        }
        
        tyre.setStockQuantity(tyre.getStockQuantity() - quantity);
        Tyre updatedTyre = tyreRepository.save(tyre);
        
        return convertToResponseDTO(updatedTyre);
    }

    /**
     * Convert Tyre entity to TyreResponseDTO
     */
    @Override
    public TyreResponseDTO convertToResponseDTO(Tyre tyre) {
        return TyreResponseDTO.builder()
                .id(tyre.getId())
                .brand(tyre.getBrand())
                .size(tyre.getSize())
                .price(tyre.getPrice())
                .stockQuantity(tyre.getStockQuantity())
                .description(tyre.getDescription())
                .createdAt(tyre.getCreatedAt())
                .updatedAt(tyre.getUpdatedAt())
                .build();
    }

    /**
     * Convert TyreRequestDTO to Tyre entity
     */
    @Override
    public Tyre convertToEntity(TyreRequestDTO tyreRequestDTO) {
        return Tyre.builder()
                .brand(tyreRequestDTO.getBrand())
                .size(tyreRequestDTO.getSize())
                .price(tyreRequestDTO.getPrice())
                .stockQuantity(tyreRequestDTO.getStockQuantity())
                .description(tyreRequestDTO.getDescription())
                .build();
    }
}
