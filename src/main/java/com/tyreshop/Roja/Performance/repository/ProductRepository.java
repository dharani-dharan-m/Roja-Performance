package com.tyreshop.Roja.Performance.repository;

import com.tyreshop.Roja.Performance.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailableTrue();
    List<Product> findByBrand(String brand);
    List<Product> findByType(String type);
    Optional<Product> findByName(String name);
    List<Product> findByQuantityGreaterThan(Integer quantity);
}
