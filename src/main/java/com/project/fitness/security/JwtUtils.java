package com.project.fitness.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {
    // private String jwtSecret = "a-string-secret-at-least-256-bits-long";
    private String jwtSecret = "YS1zdHJpbmctc2VjcmV0LWF0LWxlYXN0LTI1Ni1iaXRzLWxvbmc=";
    private int jwtExpirationMs = 172800000;

    public String getJwtFromHeaader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public String generateToken(String userId, String role){
        return Jwts.builder()
                    .subject(userId)
                    .claim("role", List.of("ROLE_" + role))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key())
                    .compact();
    }

    public boolean validateJwtToken(String jwtToken){
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public String getUserNameFromJwtToken(String jwtToken){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken).getPayload().getSubject();
    }

    public Claims getAllClaims(String jwtToken){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken).getPayload();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    
}
