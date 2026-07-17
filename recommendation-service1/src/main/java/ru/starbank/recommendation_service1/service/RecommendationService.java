package ru.starbank.recommendation_service1.service;

import com.github.benmanes.caffeine.cache.Cache;
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

    private final Cache<UUID, List<RecommendationDto>> recommendationCache;

    public RecommendationService(
            List<RecommendationRuleSet> rules,
            DynamicRuleService dynamicRuleService,
            Cache<UUID, List<RecommendationDto>> recommendationCache
    ) {
        this.rules = rules;
        this.dynamicRuleService = dynamicRuleService;
        this.recommendationCache = recommendationCache;
    }

    public List<RecommendationDto> getRecommendations(UUID userId) {

        List<RecommendationDto> cached =
                recommendationCache.getIfPresent(userId);


        if (cached != null) {
            return cached;
        }

        List<RecommendationDto> staticRecommendations =
                rules.stream()
                        .map(rule -> rule.check(userId))
                        .flatMap(java.util.Optional::stream)
                        .toList();

        List<RecommendationDto> dynamicRecommendations =
                dynamicRuleService.getRecommendations(userId);

        List<RecommendationDto> result =
                Stream.concat(
                                staticRecommendations.stream(),
                                dynamicRecommendations.stream()
                        )
                        .distinct()
                        .toList();

        recommendationCache.put(
                userId,
                result
        );


        return result;
    }

    public void clearCache() {

        recommendationCache.invalidateAll();

    }
}