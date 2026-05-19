package com.project.fitness.controller;

import com.project.fitness.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.LoginResponse;
import com.project.fitness.model.User;
import com.project.fitness.security.JwtUtils;

import lombok.RequiredArgsConstructor;

// import com.project.fitness.payload.LoginRequest;
// import com.project.fitness.payload.SignupRequest;
// import com.project.fitness.service. AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // @Autowired
    // private AuthService authService;

    // @Autowired
    // private AuthenticationManager authenticationManager;

    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticateUser(loginRequest);
            String jwtToken = jwtUtils.generateToken(user.getId(), user.getRole().name());
            return ResponseEntity.ok(
                    new LoginResponse(jwtToken, userService.mapToResponseDTO(user)));

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }
}
