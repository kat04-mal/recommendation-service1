package ru.starbank.recommendation_service1.rules;

import org.springframework.stereotype.Component;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;
import ru.starbank.recommendation_service1.constants.ProductType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500Rule implements RecommendationRuleSet {

    private static final UUID PRODUCT_ID =
            UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");

    private final RecommendationRepository repository;

    public Invest500Rule(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {

        boolean hasDebit =
                repository.hasProductType(
                        userId,
                        ProductType.DEBIT
                );

        boolean hasInvest =
                repository.hasProductType(
                        userId,
                        ProductType.INVEST
                );

        BigDecimal savingDeposit =
                repository.getDepositSum(
                        userId,
                        ProductType.SAVING
                );

        if (hasDebit
                && !hasInvest
                && savingDeposit.compareTo(BigDecimal.valueOf(1000)) > 0) {

            return Optional.of(
                    new RecommendationDto(
                            null,
                            PRODUCT_ID,
                            "Invest 500",
                            """
                            Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!
                            """
                    )
            );
        }

        return Optional.empty();
    }
}