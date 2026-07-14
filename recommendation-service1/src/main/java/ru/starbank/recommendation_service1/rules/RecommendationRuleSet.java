package ru.starbank.recommendation_service1.rules;

import ru.starbank.recommendation_service1.dto.RecommendationDto;

import java.util.Optional;
import java.util.UUID;


public interface RecommendationRuleSet {

    Optional<RecommendationDto> check(UUID userId);

}