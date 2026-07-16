package ru.starbank.recommendation_service1.service;

import org.springframework.stereotype.Service;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.rules.RecommendationRuleSet;
import ru.starbank.recommendation_service1.rules.dynamic.DynamicRuleService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Service
public class RecommendationService {


    private final List<RecommendationRuleSet> rules;

    private final DynamicRuleService dynamicRuleService;

    public RecommendationService(
            List<RecommendationRuleSet> rules,
            DynamicRuleService dynamicRuleService
    ) {
        this.rules = rules;
        this.dynamicRuleService = dynamicRuleService;
    }

    public List<RecommendationDto> getRecommendations(UUID userId) {

        List<RecommendationDto> staticRecommendations =
                rules.stream()
                        .map(rule -> rule.check(userId))
                        .flatMap(java.util.Optional::stream)
                        .toList();

        List<RecommendationDto> dynamicRecommendations =
                dynamicRuleService.getRecommendations(userId);

        return Stream.concat(
                        staticRecommendations.stream(),
                        dynamicRecommendations.stream()
                )
                .distinct()
                .toList();
    }
}