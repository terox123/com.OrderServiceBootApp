package com.OrderServiceBootApp.com.OrderServiceBootApp.services;


import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Product;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.CustomerRepository;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.OrderRepository;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductRepository productRepository;


    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));
    }


    @Transactional
    public Order create(Long customerId, List<Long> productIds) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new EntityNotFoundException("Some products not found");
        }

        for (Product product : products) {
            if (product.getQuantity() <= 0) {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }
            product.setQuantity(product.getQuantity() - 1);
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);

        return orderRepository.save(order);
    }


    @Transactional
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }


public List<Order> ordersByCustomerId(long id){
        return customerService.findCustomerById(id).getOrders();
}
}