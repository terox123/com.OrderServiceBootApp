package com.OrderServiceBootApp.com.OrderServiceBootApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JWTUtil {


    @Value("${secret_value_jwt}")
    private String secret;


    public String generateJwtToken(String username){

        LocalDateTime expDate = LocalDateTime.now().plusMinutes(60);

        return JWT.create()
                .withSubject("Customer Details")
                .withClaim("username", username)
                .withIssuedAt(new LocalDate().toDate())
                .withIssuer("Bulat")
                .withExpiresAt(Instant.from(expDate))
                .sign(Algorithm.HMAC256(secret));


    }

    public String validateTokenAndRetrieveClaim(String token){

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("Customer Details")
                .withIssuer("Bulat")
                .build();

      DecodedJWT decodedJWT =  verifier.verify(token);
       return decodedJWT.getClaim("username").asString();

    }


}
