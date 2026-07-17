package ru.starbank.recommendation_service1.rules.dynamic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.entity.RuleQueryEntity;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;
import ru.starbank.recommendation_service1.service.RuleStatsService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class DynamicRuleServiceTest {


    @Test
    void shouldIncrementStatsWhenDynamicRuleWorks() {


        RuleRepository ruleRepository =
                Mockito.mock(RuleRepository.class);


        DynamicRuleEvaluator evaluator =
                Mockito.mock(DynamicRuleEvaluator.class);


        RuleStatsService statsService =
                Mockito.mock(RuleStatsService.class);



        RuleEntity rule =
                new RuleEntity();

        rule.setId(1L);

        rule.setProductId(
                UUID.randomUUID()
        );

        rule.setProductName(
                "Кредитная карта"
        );

        rule.setProductText(
                "Описание"
        );



        RuleQueryEntity query =
                new RuleQueryEntity();

        query.setRule(rule);

        rule.setRule(
                List.of(query)
        );



        UUID userId =
                UUID.randomUUID();



        when(
                ruleRepository.findAll()
        )
                .thenReturn(
                        List.of(rule)
                );


        when(
                evaluator.check(
                        userId,
                        query
                )
        )
                .thenReturn(true);



        DynamicRuleService service =
                new DynamicRuleService(
                        ruleRepository,
                        evaluator,
                        statsService
                );



        List<RecommendationDto> result =
                service.getRecommendations(userId);



        assertEquals(
                1,
                result.size()
        );


        assertEquals(
                "Кредитная карта",
                result.get(0)
                        .getProductName()
        );


        verify(
                statsService,
                times(1)
        )
                .increment(rule);
    }
}