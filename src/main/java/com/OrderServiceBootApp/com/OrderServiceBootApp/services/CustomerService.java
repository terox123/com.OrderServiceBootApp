package com.OrderServiceBootApp.com.OrderServiceBootApp.services;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer findCustomerById(long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer with email " + email + " was not found"));
    }

    @Transactional
    public void save(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

    @Transactional
    public void update(long id, Customer updatedCustomer) {
        updatedCustomer.setId(id);
      /*  updatedCustomer.setPassword(passwordEncoder.encode(updatedCustomer.getPassword()));*/
        customerRepository.save(updatedCustomer);
    }

    public Page<Customer> findAllCustomersSortedByBirthDateDesc(Pageable pageable) {
        return customerRepository.findAllByOrderByDateOfBirthDesc(pageable);
    }

    public Page<Customer> findAllCustomersSortedByBirthDateAsc(Pageable pageable) {
        return customerRepository.findAllByOrderByDateOfBirthAsc(pageable);
    }

    @Transactional
    public void delete(long id) {
        customerRepository.deleteById(id);
    }



}