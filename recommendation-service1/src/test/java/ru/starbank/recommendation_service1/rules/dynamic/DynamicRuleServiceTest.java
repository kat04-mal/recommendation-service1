package ru.starbank.recommendation_service1.rules.dynamic;

import org.junit.jupiter.api.Test;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.entity.RuleQueryEntity;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DynamicRuleServiceTest {


    private final RuleRepository repository =
            mock(RuleRepository.class);


    private final DynamicRuleEvaluator evaluator =
            mock(DynamicRuleEvaluator.class);



    private final DynamicRuleService service =
            new DynamicRuleService(
                    repository,
                    evaluator
            );


    private final UUID userId =
            UUID.randomUUID();



    @Test
    void shouldReturnDynamicRecommendation() {


        RuleEntity rule =
                new RuleEntity();


        rule.setProductId(
                UUID.randomUUID()
        );


        rule.setProductName(
                "Простой кредит"
        );


        rule.setProductText(
                "Кредит доступен"
        );


        RuleQueryEntity query =
                new RuleQueryEntity();


        query.setQuery(
                "USER_OF"
        );


        rule.setRule(
                List.of(query)
        );


        when(repository.findAll())
                .thenReturn(
                        List.of(rule)
                );


        when(evaluator.check(
                userId,
                query
        ))
                .thenReturn(true);



        List<RecommendationDto> result =
                service.getRecommendations(userId);



        assertEquals(
                1,
                result.size()
        );


        assertEquals(
                "Простой кредит",
                result.get(0).getProductName()
        );


        assertEquals(
                "Кредит доступен",
                result.get(0).getProductText()
        );


        verify(repository)
                .findAll();


        verify(evaluator)
                .check(userId, query);
    }


    @Test
    void shouldReturnEmptyWhenRuleNotMatched() {


        RuleEntity rule =
                new RuleEntity();


        RuleQueryEntity query =
                new RuleQueryEntity();


        rule.setRule(
                List.of(query)
        );


        when(repository.findAll())
                .thenReturn(
                        List.of(rule)
                );


        when(evaluator.check(
                userId,
                query
        ))
                .thenReturn(false);



        List<RecommendationDto> result =
                service.getRecommendations(userId);



        assertTrue(
                result.isEmpty()
        );


        verify(repository)
                .findAll();


        verify(evaluator)
                .check(userId, query);
    }
}