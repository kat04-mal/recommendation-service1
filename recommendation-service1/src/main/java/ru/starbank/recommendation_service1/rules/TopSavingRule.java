package ru.starbank.recommendation_service1.rules;

import org.springframework.stereotype.Component;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {

    private static final UUID PRODUCT_ID =
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    private final RecommendationRepository repository;

    public SimpleCreditRule(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {

        boolean hasCredit =
                repository.hasProductType(userId, "CREDIT");

        BigDecimal debitDeposit =
                repository.getDepositSum(userId, "DEBIT");

        BigDecimal debitWithdraw =
                repository.getWithdrawSum(userId, "DEBIT");

        if (!hasCredit
                && debitDeposit.compareTo(debitWithdraw) > 0
                && debitWithdraw.compareTo(BigDecimal.valueOf(100000)) > 0) {

            return Optional.of(
                    new RecommendationDto(
                            PRODUCT_ID,
                            "Простой кредит",
                            "Мы предлагаем выгодный кредит."
                    )
            );
        }

        return Optional.empty();
    }
}