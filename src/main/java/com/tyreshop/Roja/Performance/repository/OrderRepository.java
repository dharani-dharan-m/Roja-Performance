package com.tyreshop.Roja.Performance.repository;

import com.tyreshop.Roja.Performance.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatus(Order.OrderStatus status);
    List<Order> findByProductId(Long productId);
}
