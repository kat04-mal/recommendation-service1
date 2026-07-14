package ru.starbank.recommendation_service1.rules;

import org.junit.jupiter.api.Test;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TopSavingRuleTest {


    private final RecommendationRepository repository =
            mock(RecommendationRepository.class);


    private final TopSavingRule rule =
            new TopSavingRule(repository);


    private final UUID userId =
            UUID.randomUUID();



    @Test
    void shouldRecommendWhenAmountExactly50000() {

        when(repository.hasProductType(userId, "DEBIT"))
                .thenReturn(true);


        when(repository.getDepositSum(userId, "SAVING"))
                .thenReturn(BigDecimal.valueOf(50000));


        when(repository.getDepositSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.ZERO);


        when(repository.getWithdrawSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.ZERO);


        assertTrue(
                rule.check(userId).isPresent()
        );
    }



    @Test
    void shouldNotRecommendWhenAmount49999() {

        when(repository.hasProductType(userId, "DEBIT"))
                .thenReturn(true);


        when(repository.getDepositSum(userId, "SAVING"))
                .thenReturn(BigDecimal.valueOf(49999));


        when(repository.getDepositSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.ZERO);


        when(repository.getWithdrawSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.ZERO);


        assertTrue(
                rule.check(userId).isEmpty()
        );
    }



    @Test
    void shouldNotRecommendWhenDebitDepositEqualsWithdraw() {

        when(repository.hasProductType(userId, "DEBIT"))
                .thenReturn(true);


        when(repository.getDepositSum(userId, "SAVING"))
                .thenReturn(BigDecimal.valueOf(50000));


        when(repository.getDepositSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.valueOf(100000));


        when(repository.getWithdrawSum(userId, "DEBIT"))
                .thenReturn(BigDecimal.valueOf(100000));


        assertTrue(
                rule.check(userId).isEmpty()
        );
    }
}