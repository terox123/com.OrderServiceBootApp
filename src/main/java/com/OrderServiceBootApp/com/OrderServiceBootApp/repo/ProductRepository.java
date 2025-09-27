
package com.OrderServiceBootApp.com.OrderServiceBootApp.repo;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}

