package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.OrderService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderService.getAll());

        return "order/index";
    }


@GetMapping("/{id}")
public String show(@PathVariable("id")long id, Model model){
        model.addAttribute("order", orderService.getById(id));
        return "order/show";
}


    @GetMapping("/new")
    public String createOrder(Model model, @PageableDefault Pageable pageable) {
        model.addAttribute("customers", customerService.findAllCustomers(pageable));
        model.addAttribute("products", productService.getAll());
        return "order/new";
    }

    @PostMapping
    public String save(@RequestParam Long customerId, @RequestParam List<Long> productIds) {
        orderService.create(customerId, productIds);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}