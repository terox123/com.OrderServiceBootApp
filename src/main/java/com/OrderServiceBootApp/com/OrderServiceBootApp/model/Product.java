package com.OrderServiceBootApp.com.OrderServiceBootApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    @JsonBackReference("order-product")
    private List<Order> orders;
}
