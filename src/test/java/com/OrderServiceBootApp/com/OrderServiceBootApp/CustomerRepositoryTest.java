package com.OrderServiceBootApp.com.OrderServiceBootApp;


import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();

       // Customer testUser1 = new Customer(1, "Bulat", "okhotnikov_99@bk.ru", 18, LocalDateTime.now(), LocalDate.now(), "male", "test", "ADMIN");
        /*Customer testUser2 = new Customer("Alice", 40, "alice@example.com", LocalDate.of(1985, 8, 22), "Female", "password456", "USER");
        Customer testUser3 = new Customer("Bob", 30, "bob@example.com", LocalDate.of(1995, 3, 10), "Male", "password789", "ADMIN");
*/

       // customerRepository.saveAll(List.of(testUser1/*, testUser2, testUser3*/));

    }



}
