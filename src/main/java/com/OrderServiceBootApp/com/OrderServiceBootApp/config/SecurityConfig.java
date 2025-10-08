package com.OrderServiceBootApp.com.OrderServiceBootApp.config;

import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomerDetailsService customerDetailsService;
    private final JWTFilter jwtFilter;


    @Autowired
    public SecurityConfig(CustomerDetailsService customerDetailsService, JWTFilter jwtFilter) {
        this.customerDetailsService = customerDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()

                )
                /*
                зарегистрироваться могут все пользователи, даже не существующие
                к страничке home имеют доступ авторизованные пользователи с ролями user, admin
                к странице управления паспортами и пользователями имеют доступ только админы
                 */
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error")
                        .permitAll()
                )
                /* страница логина, при удачной авторизации, пользователя отправляют на home страницу,
                 иначе остаётся на логине
                  */
                .logout(log -> log
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")

                )

                .userDetailsService(customerDetailsService);

        return http.build();


    }
}