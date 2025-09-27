package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class HelloController {

    private final CustomerService customerService;

    @Autowired
    public HelloController(CustomerService customerService) {
        this.customerService = customerService;
    }


}
