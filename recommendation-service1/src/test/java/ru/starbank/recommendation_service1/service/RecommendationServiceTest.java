package ru.starbank.recommendation_service1.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.Test;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.rules.RecommendationRuleSet;
import ru.starbank.recommendation_service1.rules.dynamic.DynamicRuleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class RecommendationServiceTest {


    @Test
    void shouldReturnRecommendationsFromStaticRules() {


        RecommendationRuleSet rule =
                mock(RecommendationRuleSet.class);


        DynamicRuleService dynamicRuleService =
                mock(DynamicRuleService.class);


        Cache<UUID, List<RecommendationDto>> cache =
                mock(Cache.class);


        UUID userId =
                UUID.randomUUID();



        RecommendationDto dto =
                new RecommendationDto(
                        1L,
                        UUID.randomUUID(),
                        "Топ накопление",
                        "Описание рекомендации"
                );


        when(rule.check(userId))
                .thenReturn(Optional.of(dto));


        when(dynamicRuleService.getRecommendations(userId))
                .thenReturn(List.of());


        RecommendationService service =
                new RecommendationService(
                        List.of(rule),
                        dynamicRuleService,
                        cache
                );


        List<RecommendationDto> result =
                service.getRecommendations(userId);


        assertEquals(
                1,
                result.size()
        );


        assertEquals(
                "Топ накопление",
                result.get(0)
                        .getProductName()
        );


        verify(rule)
                .check(userId);
    }



    @Test
    void shouldReturnEmptyListWhenNoRulesMatch() {


        RecommendationRuleSet rule =
                mock(RecommendationRuleSet.class);


        DynamicRuleService dynamicRuleService =
                mock(DynamicRuleService.class);


        Cache<UUID, List<RecommendationDto>> cache =
                mock(Cache.class);



        UUID userId =
                UUID.randomUUID();



        when(rule.check(userId))
                .thenReturn(Optional.empty());


        when(dynamicRuleService.getRecommendations(userId))
                .thenReturn(List.of());



        RecommendationService service =
                new RecommendationService(
                        List.of(rule),
                        dynamicRuleService,
                        cache
                );



        List<RecommendationDto> result =
                service.getRecommendations(userId);



        assertTrue(
                result.isEmpty()
        );



        verify(rule)
                .check(userId);
    }



    @Test
    void shouldReturnDynamicRecommendations() {


        DynamicRuleService dynamicRuleService =
                mock(DynamicRuleService.class);


        Cache<UUID, List<RecommendationDto>> cache =
                mock(Cache.class);



        UUID userId =
                UUID.randomUUID();



        RecommendationDto dto =
                new RecommendationDto(
                        1L,
                        UUID.randomUUID(),
                        "Простой кредит",
                        "Кредит доступен"
                );



        when(dynamicRuleService.getRecommendations(userId))
                .thenReturn(
                        List.of(dto)
                );



        RecommendationService service =
                new RecommendationService(
                        List.of(),
                        dynamicRuleService,
                        cache
                );



        List<RecommendationDto> result =
                service.getRecommendations(userId);



        assertEquals(
                1,
                result.size()
        );



        assertEquals(
                "Простой кредит",
                result.get(0)
                        .getProductName()
        );



        verify(dynamicRuleService)
                .getRecommendations(userId);
    }
}