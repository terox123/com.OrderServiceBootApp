package com.OrderServiceBootApp.com.OrderServiceBootApp.controllers;

import com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers.AuthController;
import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.CustomerDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.security.JWTUtil;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.util.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthController
 */
class AuthControllerTest {

    @Mock private CustomerValidator customerValidator;
    @Mock private CustomerService customerService;
    @Mock private ModelMapper modelMapper;
    @Mock private JWTUtil jwtUtil;
    @Mock private BindingResult bindingResult;

    @InjectMocks private AuthController authController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrationPage_returnsView() {
        String view = authController.registrationPage(new CustomerDTO());
        assertEquals("auth/registration", view);
    }

    @Test
    void login_returnsView() {
        assertEquals("auth/login", authController.login());
    }

    @Test
    void performRegistration_success() {
        CustomerDTO dto = new CustomerDTO();
        Customer mapped = new Customer();

        when(modelMapper.map(dto, Customer.class)).thenReturn(mapped);
        when(bindingResult.hasErrors()).thenReturn(false);

        String view = authController.performRegistration(dto, bindingResult);

        verify(customerService).save(mapped);
        assertEquals("redirect:/login", view);
    }

    @Test
    void performRegistration_invalidData() {
        CustomerDTO dto = new CustomerDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> authController.performRegistration(dto, bindingResult));
    }
}
