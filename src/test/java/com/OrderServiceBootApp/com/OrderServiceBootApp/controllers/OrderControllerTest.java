package com.OrderServiceBootApp.com.OrderServiceBootApp.controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers.OrderController;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.OrderService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for OrderController
 */
class OrderControllerTest {

    @Mock private OrderService orderService;
    @Mock private CustomerService customerService;
    @Mock private ProductService productService;
    @Mock private Model model;
    @Mock private Pageable pageable;

    @InjectMocks private OrderController orderController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list_returnsViewAndAddsOrders() {
        when(orderService.getAll()).thenReturn(List.of());
        String view = orderController.list(model);

        verify(model).addAttribute(eq("orders"), any());
        assertEquals("order/index", view);
    }

    @Test
    void show_returnsView() {
        when(orderService.getById(1L)).thenReturn(null);
        String view = orderController.show(1L, model);

        verify(model).addAttribute(eq("order"), any());
        assertEquals("order/show", view);
    }

    @Test
    void createOrder_returnsView() {
        String view = orderController.createOrder(model, pageable);
        verify(customerService).findAllCustomers(pageable);
        verify(productService).getAll();
        assertEquals("order/new", view);
    }

    @Test
    void save_redirectsAfterCreation() {
        String result = orderController.save(1L, List.of(1L));
        verify(orderService).create(1L, List.of(1L));
        assertEquals("redirect:/orders", result);
    }

    @Test
    void delete_redirectsAfterDeletion() {
        String result = orderController.delete(1L);
        verify(orderService).delete(1L);
        assertEquals("redirect:/orders", result);
    }
}
