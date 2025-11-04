package com.OrderServiceBootApp.com.OrderServiceBootApp.services;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CustomerService
 */
class CustomerServiceTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private CustomerService customerService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findCustomerById_found() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        assertEquals(customer, customerService.findCustomerById(1L));
    }

    @Test
    void findCustomerById_notFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.findCustomerById(1L));
    }

    @Test
    void save_encodesPassword() {
        Customer customer = new Customer();
        customer.setPassword("test");

        when(passwordEncoder.encode("test")).thenReturn("encoded");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.save(customer);
        verify(passwordEncoder).encode("test");
    }

    @Test
    void update_reencodesPassword() {
        Customer existing = new Customer();
        existing.setId(1L);

        Customer updated = new Customer();
        updated.setPassword("123");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("123")).thenReturn("encoded");
        when(customerRepository.save(any(Customer.class))).thenReturn(updated);

        Customer result = customerService.update(1L, updated);
        verify(passwordEncoder).encode("123");
    }

    @Test
    void delete_success() {
        Customer c = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(c));

        customerService.delete(1L);
        verify(customerRepository).deleteById(1L);
    }
}
