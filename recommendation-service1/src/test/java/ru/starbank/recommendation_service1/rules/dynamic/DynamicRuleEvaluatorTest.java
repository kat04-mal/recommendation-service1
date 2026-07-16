package ru.starbank.recommendation_service1.rules.dynamic;

import org.junit.jupiter.api.Test;
import ru.starbank.recommendation_service1.entity.RuleQueryEntity;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DynamicRuleEvaluatorTest {


    private final RecommendationRepository repository =
            mock(RecommendationRepository.class);


    private final DynamicRuleEvaluator evaluator =
            new DynamicRuleEvaluator(repository);


    private final UUID userId =
            UUID.randomUUID();



    @Test
    void shouldPassUserOfRule() {


        RuleQueryEntity query =
                new RuleQueryEntity();

        query.setQuery("USER_OF");
        query.setArguments(
                List.of("CREDIT")
        );
        query.setNegate(false);


        when(repository.hasProductType(
                userId,
                "CREDIT"
        ))
                .thenReturn(true);



        assertTrue(
                evaluator.check(
                        userId,
                        query
                )
        );
    }



    @Test
    void shouldFailUserOfRuleWithNegate() {


        RuleQueryEntity query =
                new RuleQueryEntity();


        query.setQuery("USER_OF");

        query.setArguments(
                List.of("CREDIT")
        );

        query.setNegate(true);



        when(repository.hasProductType(
                userId,
                "CREDIT"
        ))
                .thenReturn(true);



        assertFalse(
                evaluator.check(
                        userId,
                        query
                )
        );
    }



    @Test
    void shouldCompareTransactionSum() {


        RuleQueryEntity query =
                new RuleQueryEntity();


        query.setQuery(
                "TRANSACTION_SUM_COMPARE"
        );


        query.setArguments(
                List.of(
                        "DEBIT",
                        "WITHDRAW",
                        ">",
                        "100000"
                )
        );


        query.setNegate(false);



        when(repository.getTransactionSum(
                userId,
                "DEBIT",
                "WITHDRAW"
        ))
                .thenReturn(
                        BigDecimal.valueOf(150000)
                );



        assertTrue(
                evaluator.check(
                        userId,
                        query
                )
        );
    }



    @Test
    void shouldFailWhenTransactionSumLess() {


        RuleQueryEntity query =
                new RuleQueryEntity();


        query.setQuery(
                "TRANSACTION_SUM_COMPARE"
        );


        query.setArguments(
                List.of(
                        "DEBIT",
                        "WITHDRAW",
                        ">",
                        "100000"
                )
        );


        when(repository.getTransactionSum(
                userId,
                "DEBIT",
                "WITHDRAW"
        ))
                .thenReturn(
                        BigDecimal.valueOf(50000)
                );


        assertFalse(
                evaluator.check(
                        userId,
                        query
                )
        );
    }
}