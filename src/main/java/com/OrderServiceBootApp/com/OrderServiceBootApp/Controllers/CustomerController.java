package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.CustomerDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.kafka.KafkaProducerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.OrderService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.util.CustomerValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/customers")
public class CustomerController {

private final CustomerService customerService;
private final CustomerValidator customerValidator;
private final ModelMapper modelMapper;
private final OrderService orderService;

@Autowired
    public CustomerController(CustomerService customerService, CustomerValidator customerValidator, ModelMapper modelMapper, OrderService orderService) {
        this.customerService = customerService;
    this.customerValidator = customerValidator;
    this.modelMapper = modelMapper;
    this.orderService = orderService;
}


    @GetMapping
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false) String sort) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = switch (sort == null ? "" : sort) {
            case "old" -> customerService.findAllCustomersSortedByBirthDateAsc(pageable);
            case "young" -> customerService.findAllCustomersSortedByBirthDateDesc(pageable);
            default -> customerService.findAllCustomers(pageable);
        };

        model.addAttribute("customers", customersPage.getContent());

        return "customer/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
    Customer customer = customerService.findCustomerById(id);
    List<Order> orders = orderService.ordersByCustomerId(id);
        model.addAttribute("orders", orders);
        model.addAttribute("customer", customer);
        return "customer/show";
    }


    @GetMapping("/new")
    public String newUser(@ModelAttribute("customer") Customer customer){
        return "customer/new";
    }
/*
 проверка валидности данных, и последующее сохранение при правильно, введённых данных, иначе
 пользователь остаётся на этой же странице
 */
    @PostMapping
    public String create(@ModelAttribute("customer") @Valid CustomerDTO customerDTO, BindingResult bindingResult){
        Customer customer = convertToCustomer(customerDTO);

        customerValidator.validate(customer, bindingResult);
if(bindingResult.hasErrors()){
    return "customer/new";
}

customerService.save(customer);

return "redirect:/customers";
    }

    /*
        перенаправление на страницу изменения данных пользователя, передаётся в модель также сам user по id
         */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("customer", customerService.findCustomerById(id));
        return "customer/edit";
    }
// проверка на валидность данных, при правильных изменениях redirect на страницу с пользователями
// иначе остаётся на этой же станице

    @PostMapping("/{id}")
    public String update(@ModelAttribute("customer") @Valid CustomerDTO customerDTO,
                         @PathVariable("id") long id,
                         BindingResult bindingResult){
        Customer customer = convertToCustomer(customerDTO);
        customerValidator.validate(customer, bindingResult);
        if(bindingResult.hasErrors())
            return "customer/edit";

        customer.setId(id);
        customerService.update(customer.getId(), customer);
        return "redirect:/customers";

    }
    // перенаправление на страницу удаления пользователя
    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable("id") int id){
        model.addAttribute("customer", customerService.findCustomerById(id));
        return "customer/delete";
    }
    // удаление пользователя
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable("id") int id){
        customerService.delete(id);
        return "redirect:/customers";
    }

private Customer convertToCustomer(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO, Customer.class);
}

}
