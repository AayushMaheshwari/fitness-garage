package com.project.fitness.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.fitness.dto.RecommendationRequestDTO;
import com.project.fitness.dto.RecommendationResponseDTO;
import com.project.fitness.service.RecommendationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponseDTO> generateRecommendations(
            @RequestBody RecommendationRequestDTO request) {
        return ResponseEntity.ok(recommendationService.generateRecommendations(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationResponseDTO>> getUserRecommendations(@PathVariable String userId) {
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userId));
    }

    // @GetMapping("/activity/{activityId}")
    // public ResponseEntity<List<RecommendationResponseDTO>> getActivityRecommendations(@PathVariable String activityId) {
    //     return ResponseEntity.ok(recommendationService.getActivityRecommendations(activityId)); heelooo
    // }

}
