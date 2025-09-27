package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;


import com.OrderServiceBootApp.com.OrderServiceBootApp.kafka.KafkaProducerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
public class Cont {

    private final KafkaProducerService kafkaProducerService;
Customer customer = new Customer();


    @Autowired
    public Cont(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message){
        kafkaProducerService.sendMessage("my-topic", "key1", message);
        customer.setName("Bulat");
        return "Message was send " + message + " from " + customer.getName();
    }


}
