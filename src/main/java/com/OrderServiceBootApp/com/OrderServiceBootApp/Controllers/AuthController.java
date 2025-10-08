package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.CustomerDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.security.JWTUtil;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.util.CustomerValidator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final CustomerValidator customerValidator;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(CustomerValidator customerValidator, CustomerService customerService, ModelMapper modelMapper, JWTUtil jwtUtil) {
        this.customerValidator = customerValidator;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }



    // страница логина
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    // страница регистрации
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("customer") CustomerDTO customerDTO)
    {
        return "auth/registration";
    }

    /*
     тут проверяется валидность данных пользователя, описанных в валидаторе и entity user,
      при успешной регистрации идёт перенаправление на страницу логина
     */

    @PostMapping("/registration")
    public String performRegistration(@RequestBody @Valid CustomerDTO customerDTO,
                                      BindingResult bindingResult) {
        Customer customer = convertToCustomer(customerDTO);
        customerValidator.validate(customer, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect Data");
        }

        customerService.save(customer);




        return "redirect:/login";
    }

    private Customer convertToCustomer(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO, Customer.class);
    }
}