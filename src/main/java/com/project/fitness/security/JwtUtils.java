package com.project.fitness.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private String jwtSecret = "a-string-secret-at-least-256-bits-long";
    private int jwtExpirationMs = 172800000;

    public String getJwtFromHeaader(){
        return "";
    }

    public String generateTokenFromUsername(String userName){
        return Jwts.builder()
                    .subject(userName)
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key())
                    .compact();
    }

    public boolean validateJwtToken(String authToken){
        return true;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    
}
