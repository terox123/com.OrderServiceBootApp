package com.OrderServiceBootApp.com.OrderServiceBootApp.DTO;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.OrderRepository;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
public class CustomerDTO {
    private long id;
    private int age;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String role;
    private String password;
    private List<Order> orders;

}
