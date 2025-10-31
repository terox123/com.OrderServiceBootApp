package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.CustomerDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.kafka.KafkaProducerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.security.CustomerDetails;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerDetailsService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
public class CustomersRestController {

private final CustomerService customerService;
private final KafkaProducerService kafkaProducerService;
private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll(@PageableDefault()Pageable pageable){
List<CustomerDTO> customerDTOS = customerService.findAllCustomers(pageable)
        .stream()
        .map(this::convertToCustomerDTO)
                .toList();

kafkaProducerService.sendMessage("my-topic", "key1", "All customers are received");

return ResponseEntity.ok(customerDTOS);

    }

    @Autowired
    public CustomersRestController(CustomerService customerService, KafkaProducerService kafkaProducerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.kafkaProducerService = kafkaProducerService;
        this.modelMapper = modelMapper;
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

@PostMapping("/{id}")
    public ResponseEntity<Customer> update(@Valid CustomerDTO customerDTO, BindingResult bindingResult){
    Customer customer = convertToCustomer(customerDTO);
    if(bindingResult.hasErrors()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customer);
    }
    customerService.update(customer.getId(), customer);
    kafkaProducerService.sendMessage("my-topic", "key1", "Customer with id "
            + customer.getId() + " was updated");

    return ResponseEntity.status(HttpStatus.OK).body(customer);

}

@PostMapping("/{id}/delete")
public ResponseEntity<Customer> deleteCustomer(@PathVariable("id")long id){
    Customer customer = customerService.findCustomerById(id);
    customerService.delete(id);
    kafkaProducerService.sendMessage("my-topic", "key1", "Customer with id "
            + id + " was deleted");

    return ResponseEntity.status(HttpStatus.OK).body(customer);
}

private CustomerDTO convertToCustomerDTO(Customer customer){
    return modelMapper.map(customer, CustomerDTO.class);
}


private Customer convertToCustomer(CustomerDTO customerDTO){
    return modelMapper.map(customerDTO, Customer.class);
}

}
//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*if (authentication != null && authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String)) {

            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomerDetails) {
                CustomerDetails customerDetails = (CustomerDetails) principal;
                System.out.println(customerDetails.getUsername());
            } else if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                System.out.println(userDetails.getUsername());
            }
        } else {
            System.out.println("User not authenticated or anonymous");
        }*/

