package com.OrderServiceBootApp.com.OrderServiceBootApp.rest;

import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.OrderDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.kafka.KafkaProducerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final KafkaProducerService kafkaProducerService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<OrderDTO> getAll() {
        List<OrderDTO> orders = orderService.getAll().stream().map(this::convertToOrderDTO).toList();
        kafkaProducerService.sendMessage("my-topic", "key1", "All orders was received");
        return orders;

    }

    @GetMapping("/{id}")
    public Order getOne(@PathVariable Long id) {
        Order orderDTO = orderService.getById(id);
        kafkaProducerService.sendMessage("my-topic", "key-1", "Order with id " + id + " was received");
        return orderDTO;
    }

    @PostMapping
    public Order create(@RequestBody CreateOrderRequest request) {
        Order order =  orderService.create(request.getCustomerId(), request.getProductIds());
           kafkaProducerService.sendMessage("my-topic", "key1", "Order with id " + order.getId() + "was created");
        return order;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
        kafkaProducerService.sendMessage("my-topic", "key1", "Order with id " + id + "was deleted");
    }

    @Data
    static class CreateOrderRequest {
        private Long customerId;
        private List<Long> productIds;
    }

    private Order convertToOrder(OrderDTO orderDTO){
        return modelMapper.map(orderDTO, Order.class);
    }

    private OrderDTO convertToOrderDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }



}