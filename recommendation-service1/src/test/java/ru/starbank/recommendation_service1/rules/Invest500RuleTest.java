package ru.starbank.recommendation_service1.rules;

import org.junit.jupiter.api.Test;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class Invest500RuleTest {

    private final RecommendationRepository repository =
            mock(RecommendationRepository.class);

    private final Invest500Rule rule =
            new Invest500Rule(repository);

    private final UUID userId =
            UUID.randomUUID();


    @Test
    void shouldRecommendWhenSavingMoreThan1000() {

        when(repository.hasProductType(userId, "DEBIT"))
                .thenReturn(true);

        when(repository.hasProductType(userId, "INVEST"))
                .thenReturn(false);

        when(repository.getDepositSum(userId, "SAVING"))
                .thenReturn(BigDecimal.valueOf(1001));


        assertTrue(
                rule.check(userId).isPresent()
        );
    }


    @Test
    void shouldNotRecommendWhenSavingExactly1000() {

        when(repository.hasProductType(userId, "DEBIT"))
                .thenReturn(true);

        when(repository.hasProductType(userId, "INVEST"))
                .thenReturn(false);

        when(repository.getDepositSum(userId, "SAVING"))
                .thenReturn(BigDecimal.valueOf(1000));


        assertTrue(
                rule.check(userId).isEmpty()
        );
    }
}