package com.example.demo.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.User;

@Service
public class JwtService {

    private static final String ISSUER = "my-movie-list";
    private final String secret;
    public JwtService(@Value("${jwt.secret}") String secret){
        this.secret = secret;
    }
    
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant now = Instant.now();
        return JWT.create().
                withIssuer(ISSUER).
                withSubject(user.getEmail()).
                withClaim("userId", user.getId())
                .withIssuedAt(Date.from(now))
                //To do.withExpiresAt()
                .sign(algorithm);
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.
                require(algorithm)
                .withIssuer(ISSUER)
                .build().
                verify(token).
                getSubject();
    }
}
