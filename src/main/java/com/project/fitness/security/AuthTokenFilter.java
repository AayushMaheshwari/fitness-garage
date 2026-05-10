package com.project.fitness.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    // @Autowired
    // private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("AuthTokenFilter Called");
        try{
            String jwtToken = parseJwt(request);

            if(jwtToken != null && jwtUtils.validateJwtToken(jwtToken)){
                System.out.println("JWT Token: " + jwtToken);
                String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
                //UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                Claims claims = jwtUtils.getAllClaims(jwtToken);
                List<String> roles = claims.get("roles", List.class);
                List<GrantedAuthority> authorities = List.of();
                if(roles != null){
                    authorities = roles.stream().map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role)).toList();
                } 

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    public String parseJwt(HttpServletRequest request){
        return jwtUtils.getJwtFromHeaader(request);
    }
}
