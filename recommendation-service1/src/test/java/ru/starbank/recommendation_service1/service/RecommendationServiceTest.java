package ru.starbank.recommendation_service1.service;

import org.junit.jupiter.api.Test;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.rules.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class RecommendationServiceTest {


    @Test
    void shouldReturnRecommendationsFromRules() {

        RecommendationRuleSet rule =
                mock(RecommendationRuleSet.class);


        UUID userId =
                UUID.randomUUID();


        RecommendationDto dto =
                new RecommendationDto(
                        UUID.randomUUID(),
                        "Топ накопление",
                        "Описание рекомендации"
                );


        when(rule.check(userId))
                .thenReturn(Optional.of(dto));


        RecommendationService service =
                new RecommendationService(
                        List.of(rule)
                );


        List<RecommendationDto> result =
                service.getRecommendations(userId);


        assertEquals(
                1,
                result.size()
        );


        assertEquals(
                "Топ накопление",
                result.get(0).getName()
        );


        verify(rule)
                .check(userId);
    }



    @Test
    void shouldReturnEmptyListWhenNoRulesMatch() {

        RecommendationRuleSet rule =
                mock(RecommendationRuleSet.class);


        UUID userId =
                UUID.randomUUID();


        when(rule.check(userId))
                .thenReturn(Optional.empty());


        RecommendationService service =
                new RecommendationService(
                        List.of(rule)
                );


        List<RecommendationDto> result =
                service.getRecommendations(userId);


        assertTrue(
                result.isEmpty()
        );


        verify(rule)
                .check(userId);
    }
}