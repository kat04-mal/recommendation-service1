package ru.starbank.recommendation_service1.service;

import org.springframework.stereotype.Service;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.rules.RecommendationRuleSet;

import java.util.List;
import java.util.UUID;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;


    public RecommendationService(List<RecommendationRuleSet> rules) {
        this.rules = rules;
    }


    public List<RecommendationDto> getRecommendations(UUID userId) {

        return rules.stream()
                .map(rule -> rule.check(userId))
                .flatMap(java.util.Optional::stream)
                .toList();
    }
}