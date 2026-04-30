package com.tyreshop.Roja.Performance.service;

import com.tyreshop.Roja.Performance.model.Product;
import com.tyreshop.Roja.Performance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByAvailableTrue();
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    public List<Product> getProductsByType(String type) {
        return productRepository.findByType(type);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setSize(product.getSize());
            existingProduct.setType(product.getType());
            existingProduct.setAvailable(product.getAvailable());
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public boolean updateStock(Long id, Integer quantity) {
        return productRepository.findById(id).map(product -> {
            product.setQuantity(product.getQuantity() - quantity);
            if (product.getQuantity() <= 0) {
                product.setAvailable(false);
            }
            productRepository.save(product);
            return true;
        }).orElse(false);
    }
}
