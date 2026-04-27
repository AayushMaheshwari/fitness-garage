package com.project.fitness.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.fitness.dto.ActivityRequestDTO;
import com.project.fitness.dto.ActivityResponseDTO;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponseDTO createActivity(ActivityRequestDTO activityRequestDTO) {
        User user = userRepository.findById(activityRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + activityRequestDTO.getUserId()));

        Activity activity = Activity.builder()
                .user(user)
                .type(activityRequestDTO.getType())
                .additionalMetrics(activityRequestDTO.getAdditionalMetrics())
                .duration(activityRequestDTO.getDuration())
                .caloriesBurned(activityRequestDTO.getCaloriesBurned())
                .startTime(activityRequestDTO.getStartTime())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        return mapToResponseDTO(savedActivity);
    }

    private ActivityResponseDTO mapToResponseDTO(Activity activity) {
        ActivityResponseDTO responseDTO = new ActivityResponseDTO();
        responseDTO.setId(activity.getId());
        responseDTO.setUserId(activity.getUser().getId());
        responseDTO.setActivityType(activity.getType());
        responseDTO.setAdditionalMetrics(activity.getAdditionalMetrics());
        responseDTO.setDurationMinutes(activity.getDuration());
        responseDTO.setCaloriesBurned(activity.getCaloriesBurned());
        responseDTO.setStartTime(activity.getStartTime());
        responseDTO.setCreatedAt(activity.getCreatedAt());
        responseDTO.setUpdatedAt(activity.getUpdatedAt());
        return responseDTO;
    }

    public List<ActivityResponseDTO> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }
}
