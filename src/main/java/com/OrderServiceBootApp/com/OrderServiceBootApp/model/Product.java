package com.OrderServiceBootApp.com.OrderServiceBootApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
}
