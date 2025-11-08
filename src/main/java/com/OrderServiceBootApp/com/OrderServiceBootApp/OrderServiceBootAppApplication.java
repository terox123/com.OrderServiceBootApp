package com.OrderServiceBootApp.com.OrderServiceBootApp;


/*import com.OrderServiceBootApp.com.OrderServiceBootApp.services.GreetingServiceImpl;*/
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


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




    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }






}
