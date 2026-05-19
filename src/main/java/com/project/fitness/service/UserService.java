package com.project.fitness.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.UserRequestDTO;
import com.project.fitness.dto.UserResponseDTO;
import com.project.fitness.model.User;
import com.project.fitness.model.UserRole;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        UserRole role = userRequestDTO.getRole() == null ? UserRole.USER : userRequestDTO.getRole();

        User user = User.builder()
                .email(userRequestDTO.getEmail())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }

    public UserResponseDTO mapToResponseDTO(User savedUser) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(savedUser.getId());
        userResponseDTO.setEmail(savedUser.getEmail());
        userResponseDTO.setPassword(savedUser.getPassword());
        userResponseDTO.setFirstName(savedUser.getFirstName());
        userResponseDTO.setLastName(savedUser.getLastName());
        userResponseDTO.setCreatedAt(savedUser.getCreatedAt());
        userResponseDTO.setUpdatedAt(savedUser.getUpdatedAt());
        return userResponseDTO;
    }

    public User authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
            if(user == null){
                throw new RuntimeException("Invalid Credentials");
            }

            if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
                throw new RuntimeException("Invalid Credentials");
            }
            return user;
    }
}
