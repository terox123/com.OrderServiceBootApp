package com.OrderServiceBootApp.com.OrderServiceBootApp.DTO;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private LocalDateTime createdAt;
    private String customerName;
    private List<Product> productNames;


    public OrderDTO(Order order) {
        this.createdAt = order.getCreatedAt();
        this.customerName = order.getCustomer().getName();
        this.productNames = order.getProducts();
    }
}




