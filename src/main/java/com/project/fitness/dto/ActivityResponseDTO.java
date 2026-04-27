package com.project.fitness.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.project.fitness.model.ActivityType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponseDTO {
    private String id;
    private String userId;
    private ActivityType activityType;
    private Map<String, Object> additionalMetrics;
    private Integer durationMinutes;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
