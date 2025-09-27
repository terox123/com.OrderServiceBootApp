package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.CustomerDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.kafka.KafkaProducerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersRestController {

private final CustomerService customerService;
private final KafkaProducerService kafkaProducerService;

@Autowired
    public CustomersRestController(CustomerService customerService, KafkaProducerService kafkaProducerService) {
        this.customerService = customerService;
    this.kafkaProducerService = kafkaProducerService;
}

    @GetMapping
    public ResponseEntity<Page<Customer>> getAll(@PageableDefault()Pageable pageable){

    return ResponseEntity.ok(customerService.findAllCustomers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable("id")int id){

    return ResponseEntity.ok(customerService.findCustomerById(id));
    }



}
