package com.project.fitness.service;


import org.springframework.stereotype.Service;

import com.project.fitness.dto.UserRequestDTO;
import com.project.fitness.dto.UserResponseDTO;
import com.project.fitness.model.User;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        User user = User.builder()
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .build();

        User savedUser = userRepository.save(user);
        return mapToResponseDTO(savedUser);
    }

    private UserResponseDTO mapToResponseDTO(User savedUser) {
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
}
