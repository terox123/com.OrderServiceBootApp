package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.CustomerDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.kafka.KafkaProducerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.CustomerRepository;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomersRestController {

private final CustomerService customerService;
private final KafkaProducerService kafkaProducerService;
private final ModelMapper modelMapper;

@Autowired
    public CustomersRestController(CustomerService customerService, KafkaProducerService kafkaProducerService, ModelMapper modelMapper) {
        this.customerService = customerService;
    this.kafkaProducerService = kafkaProducerService;
    this.modelMapper = modelMapper;
}

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll(@PageableDefault()Pageable pageable){
List<CustomerDTO> customerDTOS = customerService.findAllCustomers(pageable)
        .stream()
        .map(this::convertToCustomerDTO)
                .toList();

kafkaProducerService.sendMessage("my-topic", "key1", "All customers are received");

return ResponseEntity.ok(customerDTOS);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable("id")long id){
CustomerDTO customerDTO = convertToCustomerDTO(customerService.findCustomerById(id));
kafkaProducerService.sendMessage("my-topic", "key1","Customer with id " + id + " was received");
return ResponseEntity.ok(customerDTO);

    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer, BindingResult bindingResult){

    if(bindingResult.hasErrors()){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customer);
    }

    customerService.save(customer);
    kafkaProducerService.sendMessage("my-topic", "key1",
            "Customer with id " + customer.getId() + " was created");



    return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }





private CustomerDTO convertToCustomerDTO(Customer customer){
    return modelMapper.map(customer, CustomerDTO.class);
}

}
