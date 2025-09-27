package com.OrderServiceBootApp.com.OrderServiceBootApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class OrderServiceBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceBootAppApplication.class, args);
	}

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }






}
