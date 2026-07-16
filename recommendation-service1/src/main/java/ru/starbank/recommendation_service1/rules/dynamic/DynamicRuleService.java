package ru.starbank.recommendation_service1.rules.dynamic;

import org.springframework.stereotype.Service;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {


    private final RuleRepository ruleRepository;

    private final DynamicRuleEvaluator evaluator;


    public DynamicRuleService(
            RuleRepository ruleRepository,
            DynamicRuleEvaluator evaluator
    ) {
        this.ruleRepository = ruleRepository;
        this.evaluator = evaluator;
    }



    public List<RecommendationDto> getRecommendations(
            UUID userId
    ) {

        return ruleRepository.findAll()

                .stream()

                .filter(rule ->
                        checkRule(
                                userId,
                                rule
                        )
                )

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
