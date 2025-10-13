package com.OrderServiceBootApp.com.OrderServiceBootApp.repo;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Page<Customer> findAllByOrderByDateOfBirthDesc(Pageable pageable);
    Page<Customer> findAllByOrderByDateOfBirthAsc(Pageable pageable);

    Optional<Customer> findByName(String name);

}
