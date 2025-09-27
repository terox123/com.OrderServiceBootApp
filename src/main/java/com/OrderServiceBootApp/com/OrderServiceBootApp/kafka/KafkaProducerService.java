package com.OrderServiceBootApp.com.OrderServiceBootApp.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

@Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String key, String message){
    kafkaTemplate.send(topic, key, message);
        System.out.printf("Sent message: key=%s value=%s%n", key, message);
    }


}
