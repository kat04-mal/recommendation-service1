package ru.starbank.recommendation_service1.rules.dynamic;

import org.springframework.stereotype.Component;
import ru.starbank.recommendation_service1.entity.RuleQueryEntity;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public class DynamicRuleEvaluator {

    private final RecommendationRepository repository;


    public DynamicRuleEvaluator(
            RecommendationRepository repository
    ) {
        this.repository = repository;
    }

    public boolean check(
            UUID userId,
            RuleQueryEntity query
    ) {

        List<String> args = query.getArguments();

        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException(
                    "Rule arguments empty for " + query.getQuery()
            );
        }

        boolean result;

        switch (query.getQuery()) {


            case "USER_OF" -> {

                String productType = args.get(0);

                result =
                        repository.hasProductType(
                                userId,
                                productType
                        );
            }

            case "ACTIVE_USER_OF" -> {

                String productType = args.get(0);


                result =
                        repository.countProducts(
                                userId,
                                productType
                        ) >= 5;
            }

            case "TRANSACTION_SUM_COMPARE" -> {

                String productType = args.get(0);

                String transactionType = args.get(1);

                String operation = args.get(2);


                BigDecimal value =
                        new BigDecimal(args.get(3));

                BigDecimal sum =
                        repository.getTransactionSum(
                                userId,
                                productType,
                                transactionType
                        );

                result =
                        compare(
                                sum,
                                value,
                                operation
                        );

            }

            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> {

                String productType = args.get(0);

                String operation = args.get(1);

                BigDecimal deposit =
                        repository.getDepositSum(
                                userId,
                                productType
                        );

                BigDecimal withdraw =
                        repository.getWithdrawSum(
                                userId,
                                productType
                        );

                result =
                        compare(
                                deposit,
                                withdraw,
                                operation
                        );
            }

            default -> throw new IllegalArgumentException(
                    "Unknown query: " + query.getQuery()
            );
        }

        return query.isNegate()
                ? !result
                : result;
    }



    private boolean compare(
            BigDecimal first,
            BigDecimal second,
            String operation
    ){

        int result =
                first.compareTo(second);


        return switch(operation){

            case ">" -> result > 0;

            case "<" -> result < 0;

            case "=" -> result == 0;

            case ">=" -> result >= 0;

            case "<=" -> result <= 0;

            default ->
                    throw new IllegalArgumentException(
                            "Unknown operation "
                                    + operation
                    );
        };
    }
}