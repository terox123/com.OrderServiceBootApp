package com.OrderServiceBootApp.com.OrderServiceBootApp.config;

import com.OrderServiceBootApp.com.OrderServiceBootApp.security.JWTUtil;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerDetailsService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, CustomerDetailsService customerDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customerDetailsService = customerDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7);
        try{
            String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
            UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(customerDetailsService,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                            );

            SecurityContextHolder.getContext().setAuthentication(authToken);



        }catch (JWTVerificationException ex){
            throw new ServletException();
        }



        }
filterChain.doFilter(request, response);
    }
}