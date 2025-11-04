package com.OrderServiceBootApp.com.OrderServiceBootApp.DTO;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Data
public class ProductDTO {


    private String name;
    private Integer price;
    private Integer quantity;
    private List<Order> orders;
}
