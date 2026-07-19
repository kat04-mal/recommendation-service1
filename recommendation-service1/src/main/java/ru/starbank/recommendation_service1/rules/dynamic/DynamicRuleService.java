package ru.starbank.recommendation_service1.rules.dynamic;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;
import ru.starbank.recommendation_service1.service.RuleStatsService;

import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {

    private final RuleRepository ruleRepository;

    private final DynamicRuleEvaluator evaluator;

    private final RuleStatsService statsService;

    public DynamicRuleService(
            RuleRepository ruleRepository,
            DynamicRuleEvaluator evaluator,
            RuleStatsService statsService
    ) {
        this.ruleRepository = ruleRepository;
        this.evaluator = evaluator;
        this.statsService = statsService;
    }

    @Transactional
    public List<RecommendationDto> getRecommendations(
            UUID userId
    ) {

        return ruleRepository.findAll()
                .stream()

                .filter(rule -> {

                    boolean result = checkRule(
                            userId,
                            rule
                    );

                    if (result) {
                        statsService.increment(rule);
                    }

                    return result;

                })


                .map(rule ->
                        new RecommendationDto(
                                rule.getId(),
                                rule.getProductId(),
                                rule.getProductName(),
                                rule.getProductText()
                        )
                )

                .toList();
    }

    private boolean checkRule(
            UUID userId,
            RuleEntity rule
    ) {

        return rule.getRule()
                .stream()
                .allMatch(query ->
                        evaluator.check(
                                userId,
                                query
                        )
                );
    }

}