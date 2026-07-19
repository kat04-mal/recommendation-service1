package ru.starbank.recommendation_service1.rules;

import org.junit.jupiter.api.Test;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SimpleCreditRuleTest {


    private final RecommendationRepository repository =
            mock(RecommendationRepository.class);


    private final SimpleCreditRule rule =
            new SimpleCreditRule(repository);


    private final UUID userId =
            UUID.randomUUID();



    @Test
    void shouldRecommendWhenWithdrawMoreThan100000() {


        when(repository.hasProductType(userId, "CREDIT"))
                .thenReturn(false);


        when(repository.getDepositSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.valueOf(200000));


        when(repository.getWithdrawSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.valueOf(100001));


        assertTrue(
                rule.check(userId).isPresent()
        );
    }



    @Test
    void shouldNotRecommendWhenWithdrawExactly100000() {


        when(repository.hasProductType(userId, "CREDIT"))
                .thenReturn(false);


        when(repository.getDepositSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.valueOf(200000));


        when(repository.getWithdrawSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.valueOf(100000));


        assertTrue(
                rule.check(userId).isEmpty()
        );
    }
}