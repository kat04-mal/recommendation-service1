package ru.starbank.recommendation_service1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class RecommendationResponse {

    @JsonProperty("user_id")
    private UUID userId;

    private List<RecommendationDto> recommendations;


    public RecommendationResponse(
            UUID userId,
            List<RecommendationDto> recommendations
    ) {
        this.userId = userId;
        this.recommendations = recommendations;
    }


    public UUID getUserId() {
        return userId;
    }


    public List<RecommendationDto> getRecommendations() {
        return recommendations;
    }
}