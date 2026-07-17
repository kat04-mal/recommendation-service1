package ru.starbank.recommendation_service1.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.starbank.recommendation_service1.dto.rule.RuleStatsResponse;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.entity.RuleStatsEntity;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;
import ru.starbank.recommendation_service1.repository.rule.RuleStatsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class RuleStatsServiceTest {


    @Test
    void shouldIncrementRuleCounter() {

        RuleStatsRepository statsRepository =
                Mockito.mock(RuleStatsRepository.class);

        RuleRepository ruleRepository =
                Mockito.mock(RuleRepository.class);


        RuleEntity rule =
                new RuleEntity();

        rule.setId(1L);


        RuleStatsService service =
                new RuleStatsService(
                        statsRepository,
                        ruleRepository
                );


        service.increment(rule);


        verify(statsRepository, times(1))
                .save(any());
    }



    @Test
    void shouldReturnAllRulesWithZeroCount() {


        RuleStatsRepository statsRepository =
                Mockito.mock(RuleStatsRepository.class);

        RuleRepository ruleRepository =
                Mockito.mock(RuleRepository.class);



        RuleEntity rule =
                new RuleEntity();

        rule.setId(10L);



        when(ruleRepository.findAll())
                .thenReturn(
                        List.of(rule)
                );



        RuleStatsService service =
                new RuleStatsService(
                        statsRepository,
                        ruleRepository
                );



        RuleStatsResponse response =
                service.getStats();



        assertEquals(
                1,
                response.getStats().size()
        );


        assertEquals(
                10L,
                response.getStats()
                        .get(0)
                        .getRule_id()
        );


        assertEquals(
                0,
                response.getStats()
                        .get(0)
                        .getCount()
        );
    }
}