package com.project.fitness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.security.JwtUtils;

// import com.project.fitness.payload.LoginRequest;
// import com.project.fitness.payload.SignupRequest;
// import com.project.fitness.service. AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // @Autowired
    // private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    // public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
    public String authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // String jwtToken = authService.generateToken(authentication);
            String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

            return jwtToken;

            // return ResponseEntity.ok(
            //         "Login successful! Token: " + jwtToken);

        } catch (AuthenticationException e) {
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            //         .body("Invalid email or password");
            e.printStackTrace();
            return "Invalid email or password";
        }
    }

    // @PostMapping("/signup")
    // public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
    //     try {
    //         authService.registerUser(signUpRequest);
    //         return ResponseEntity.ok("User registered successfully!");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(e.getMessage());
    //     }
    // }
}
