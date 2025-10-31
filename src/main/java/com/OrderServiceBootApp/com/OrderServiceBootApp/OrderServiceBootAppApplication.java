package com.OrderServiceBootApp.com.OrderServiceBootApp;


/*import com.OrderServiceBootApp.com.OrderServiceBootApp.services.GreetingServiceImpl;*/
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableCaching
public class OrderServiceBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceBootAppApplication.class, args);
	}

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

   /* @Bean
    public Server server(){
        return ServerBuilder.forPort(8080).addService(new GreetingServiceImpl()).build();

    }*/





}
