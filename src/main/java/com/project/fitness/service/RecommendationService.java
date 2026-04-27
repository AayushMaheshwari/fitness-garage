package com.project.fitness.service;

import org.springframework.stereotype.Service;

import com.project.fitness.dto.RecommendationRequestDTO;
import com.project.fitness.dto.RecommendationResponseDTO;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationRepository;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final UserRepository userRepository;
    private final RecommendationRepository recommendationRepository;
    private final ActivityRepository activityRepository;

    public RecommendationResponseDTO generateRecommendations(RecommendationRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + request.getActivityId()));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .activity(activity)
                .type(request.getType())
                .recommendation(request.getRecommendation())
                .improvements(request.getImprovements())
                .suggestions(request.getSuggestions())
                .safety(request.getSafety())
                .build();

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);

        return mapToResponseDTO(savedRecommendation);
    }

    private RecommendationResponseDTO mapToResponseDTO(Recommendation recommendation) {
        RecommendationResponseDTO responseDTO = new RecommendationResponseDTO();
        responseDTO.setId(recommendation.getId());
        responseDTO.setUserId(recommendation.getUser().getId());
        responseDTO.setActivityId(recommendation.getActivity().getId());
        responseDTO.setType(recommendation.getType());
        responseDTO.setRecommendation(recommendation.getRecommendation());
        responseDTO.setImprovements(recommendation.getImprovements());
        responseDTO.setSuggestions(recommendation.getSuggestions());
        responseDTO.setSafety(recommendation.getSafety());
        responseDTO.setCreatedAt(recommendation.getCreatedAt());
        responseDTO.setUpdatedAt(recommendation.getUpdatedAt());
        return responseDTO;
    }
}
