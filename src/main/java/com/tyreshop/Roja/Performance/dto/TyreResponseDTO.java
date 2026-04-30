package com.tyreshop.Roja.Performance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TyreResponseDTO - Data Transfer Object for tyre API responses
 * 
 * @author Roja Performance
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TyreResponseDTO {

    private Long id;

    private String brand;

    private String size;

    private Double price;

    private Integer stockQuantity;

    private String description;

    private Long createdAt;

    private Long updatedAt;
}
