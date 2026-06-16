package com.tyreshop.Roja.Performance.service;

import com.tyreshop.Roja.Performance.model.Customer;
import com.tyreshop.Roja.Performance.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getActiveCustomers() {
        return customerRepository.findByActiveTrue();
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getCustomersByCity(String city) {
        return customerRepository.findByCity(city);
    }

    public Customer createCustomer(Customer customer) {
        validateCustomer(customer);
        customerRepository.findByEmail(customer.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Customer email already exists");
        });
        if (customer.getActive() == null) {
            customer.setActive(true);
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        validateCustomer(customer);
        return customerRepository.findById(id).map(existingCustomer -> {
            customerRepository.findByEmail(customer.getEmail())
                    .filter(found -> !found.getId().equals(id))
                    .ifPresent(found -> {
                        throw new IllegalArgumentException("Customer email already exists");
                    });
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhone(customer.getPhone());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setCity(customer.getCity());
            existingCustomer.setState(customer.getState());
            existingCustomer.setZipCode(customer.getZipCode());
            existingCustomer.setActive(customer.getActive() != null ? customer.getActive() : true);
            return customerRepository.save(existingCustomer);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private void validateCustomer(Customer customer) {
        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Customer first name is required");
        }
        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new IllegalArgumentException("Customer last name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException("Customer email is required");
        }
        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new IllegalArgumentException("Customer phone is required");
        }
    }
}
