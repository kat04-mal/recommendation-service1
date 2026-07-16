package ru.starbank.recommendation_service1.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;


@Getter
public class RecommendationResponse {

    private final UUID userId;

    private final List<RecommendationDto> recommendations;

    public RecommendationResponse(
            UUID userId,
            List<RecommendationDto> recommendations
    ) {

        this.userId = userId;
        this.recommendations = recommendations;

    }
}