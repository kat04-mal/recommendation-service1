package ru.starbank.recommendation_service1.controller;

import org.springframework.web.bind.annotation.*;
import ru.starbank.recommendation_service1.dto.RecommendationResponse;
import ru.starbank.recommendation_service1.service.RecommendationService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {


    private final RecommendationService service;


    public RecommendationController(RecommendationService service) {
        this.service = service;
    }


    @GetMapping("/{userId}")
    public RecommendationResponse getRecommendations(
            @PathVariable UUID userId
    ) {

        return new RecommendationResponse(
                userId,
                service.getRecommendations(userId)
        );
    }
}