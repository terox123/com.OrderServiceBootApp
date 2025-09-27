package com.OrderServiceBootApp.com.OrderServiceBootApp.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {


    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void listen(String message){
        System.out.printf("Received message: %s%n", message);
    }

}
