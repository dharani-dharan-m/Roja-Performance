package com.tyreshop.Roja.Performance.service;

import com.tyreshop.Roja.Performance.model.Order;
import com.tyreshop.Roja.Performance.model.Customer;
import com.tyreshop.Roja.Performance.model.Product;
import com.tyreshop.Roja.Performance.repository.CustomerRepository;
import com.tyreshop.Roja.Performance.repository.OrderRepository;
import com.tyreshop.Roja.Performance.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByProductId(Long productId) {
        return orderRepository.findByProductId(productId);
    }

    public Order createOrder(Order order) {
        validateOrder(order);
        order.setCustomer(resolveCustomer(order));
        order.setProduct(resolveProduct(order));
        if (order.getStatus() == null) {
            order.setStatus(Order.OrderStatus.PENDING);
        }
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        validateOrder(order);
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setCustomer(resolveCustomer(order));
            existingOrder.setProduct(resolveProduct(order));
            existingOrder.setQuantity(order.getQuantity());
            existingOrder.setTotalPrice(order.getTotalPrice());
            existingOrder.setStatus(order.getStatus() != null ? order.getStatus() : existingOrder.getStatus());
            existingOrder.setShippingAddress(order.getShippingAddress());
            existingOrder.setDeliveryDate(order.getDeliveryDate());
            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        return orderRepository.findById(id).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private void validateOrder(Order order) {
        if (order.getCustomer() == null || order.getCustomer().getId() == null) {
            throw new IllegalArgumentException("Customer is required for an order");
        }
        if (order.getProduct() == null || order.getProduct().getId() == null) {
            throw new IllegalArgumentException("Product is required for an order");
        }
        if (order.getQuantity() == null || order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Order quantity must be greater than zero");
        }
        if (order.getTotalPrice() == null || order.getTotalPrice() < 0) {
            throw new IllegalArgumentException("Order total price cannot be negative");
        }
    }

    private Customer resolveCustomer(Order order) {
        return customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Selected customer does not exist"));
    }

    private Product resolveProduct(Order order) {
        return productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Selected product does not exist"));
    }
}
