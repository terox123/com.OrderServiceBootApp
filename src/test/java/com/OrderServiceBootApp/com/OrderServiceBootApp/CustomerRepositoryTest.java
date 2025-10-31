package com.OrderServiceBootApp.com.OrderServiceBootApp;  // Подставь свой пакет

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();  // Очистка для изоляции

        Customer customer1 = new Customer("Bulat", 19, "okhotnikov_999@bk.ru", LocalDate.of(2006, 4, 24), "Male", "Bilut2006b", "ADMIN");
        Customer customer2 = new Customer("Liona", 18, "lionami2lite@gmail.com", LocalDate.of(2006, 11, 6), "Female", "Liona", "USER");
        customerRepository.saveAll(List.of(customer1, customer2));
    }

    @Test
    void findByName_ShouldReturnCustomer_WhenExists() {
        Optional<Customer> found = customerRepository.findByName("Bulat");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("okhotnikov_999@bk.ru");
    }

    @Test
    void findByName_ShouldNotReturn_WhenNotExists() {
        Optional<Customer> found = customerRepository.findByName("John");

        assertThat(found).isEmpty();
    }

    @Test
    void findByEmail_ShouldReturn_WhenExists() {
        Optional<Customer> found = customerRepository.findByEmail("okhotnikov_999@bk.ru");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Bulat");
    }

    @Test
    void findByEmail_ShouldNotReturn_WhenNotExists() {
        Optional<Customer> found = customerRepository.findByEmail("okhotnikov_9999@bk.ru");

        assertThat(found).isEmpty();
    }

    @Test
    void save_ShouldCreateCustomerWithGeneratedId() {
        Customer newCustomer = new Customer("NewUser", 20, "new@example.com", LocalDate.of(2000, 1, 1), "Male", "pass123", "USER");

        Customer saved = customerRepository.save(newCustomer);

        assertNotNull(saved.getId());  // ID сгенерирован
        assertEquals("NewUser", saved.getName());
        assertEquals(3, customerRepository.count());  // +1 к исходным 2
    }

    @Test
    void findById_ShouldReturnCustomer_WhenExists() {
        Customer savedCustomer = customerRepository.findAll().get(0);  // Первый из setUp
        Long id = savedCustomer.getId();

        Optional<Customer> found = customerRepository.findById(id);

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Bulat");
    }

    @Test
    void findById_ShouldNotReturn_WhenNotExists() {
        Optional<Customer> found = customerRepository.findById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void findAll_ShouldReturnAllCustomers() {
        List<Customer> all = customerRepository.findAll();

        assertEquals(2, all.size());
        assertThat(all).extracting(Customer::getName).contains("Bulat", "Liona");
    }

    @Test
    void updateCustomer_ShouldChangeFields() {
        Customer customerToUpdate = customerRepository.findAll().get(0);
        customerToUpdate.setName("Bulat Updated");
        customerToUpdate.setAge(20);

        Customer updated = customerRepository.save(customerToUpdate);

        assertEquals("Bulat Updated", updated.getName());
        assertEquals(20, updated.getAge());

        Optional<Customer> reloaded = customerRepository.findById(updated.getId());
        assertThat(reloaded).isPresent();
        assertThat(reloaded.get().getName()).isEqualTo("Bulat Updated");
    }

    @Test
    void deleteById_ShouldRemoveCustomer() {

        Customer customerToDelete = customerRepository.findAll().get(0);
        Long id = customerToDelete.getId();

        customerRepository.deleteById(id);

        assertThat(customerRepository.findById(id)).isEmpty();
        assertEquals(1, customerRepository.count());
        assertFalse(customerRepository.existsById(id));
    }

    @Test
    void count_ShouldReturnCorrectNumber() {
        assertEquals(2, customerRepository.count());
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        Customer existing = customerRepository.findAll().get(0);
        assertTrue(customerRepository.existsById(existing.getId()));
    }

    @Test
    void existsById_ShouldReturnFalse_WhenNotExists() {
        assertFalse(customerRepository.existsById(999L));
    }
}
