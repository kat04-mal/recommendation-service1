package ru.starbank.recommendation_service1.rules;

import org.springframework.stereotype.Component;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;
import ru.starbank.recommendation_service1.constants.ProductType;

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
                repository.hasProductType(
                        userId,
                        ProductType.CREDIT
                );


        BigDecimal debitDeposit =
                repository.getDepositSum(
                        userId,
                        ProductType.DEBIT
                );


        BigDecimal debitWithdraw =
                repository.getWithdrawSum(
                        userId,
                        ProductType.DEBIT
                );

        if (!hasCredit
                && debitDeposit.compareTo(debitWithdraw) > 0
                && debitWithdraw.compareTo(BigDecimal.valueOf(100000)) > 0) {

            return Optional.of(
                    new RecommendationDto(
                            PRODUCT_ID,
                            "Простой кредит",
                            "Откройте мир выгодных кредитов с нами!\n" +
                                    "\n" +
                                    "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" +
                                    "\n" +
                                    "Почему выбирают нас:\n" +
                                    "\n" +
                                    "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" +
                                    "\n" +
                                    "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
                                    "\n" +
                                    "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" +
                                    "\n" +
                                    "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
                    )
            );
        }

        return Optional.empty();
    }
}