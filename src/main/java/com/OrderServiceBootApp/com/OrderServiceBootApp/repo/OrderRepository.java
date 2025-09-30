
package com.OrderServiceBootApp.com.OrderServiceBootApp.repo;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findOrdersByCustomer_Id(Long customerId, Pageable pageable);
}
