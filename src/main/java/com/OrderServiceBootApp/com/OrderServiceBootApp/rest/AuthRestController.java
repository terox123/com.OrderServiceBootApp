package com.OrderServiceBootApp.com.OrderServiceBootApp.rest;

import com.OrderServiceBootApp.com.OrderServiceBootApp.security.JWTUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthRestController(JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        String token = jwtUtil.generateJwtToken(authentication.getName());
        System.out.println(authentication.getAuthorities() + "\n" + authentication.getName());

        return ResponseEntity.ok(new AuthResponse(token));
    }


}
@Getter
class AuthRequest{
    private String username;
    private String password;
}
@Getter
class AuthResponse{
    private final String token;

    AuthResponse(String token) {
        this.token = token;
    }
}