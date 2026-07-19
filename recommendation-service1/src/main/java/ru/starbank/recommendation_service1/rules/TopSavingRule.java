package ru.starbank.recommendation_service1.rules;

import org.springframework.stereotype.Component;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.repository.RecommendationRepository;
import ru.starbank.recommendation_service1.constants.ProductType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * Статическое правило рекомендации продукта "Топ накопление".
 *
 * <p>Правило срабатывает, если:
 * <ul>
 *     <li>у пользователя есть дебетовый продукт;</li>
 *     <li>сумма накоплений или депозитов >= 50000;</li>
 *     <li>сумма пополнений превышает сумму снятий.</li>
 * </ul>
 */
@Component
public class TopSavingRule implements RecommendationRuleSet {

    private static final UUID PRODUCT_ID =
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

    private final RecommendationRepository repository;

    public TopSavingRule(RecommendationRepository repository) {
        this.repository = repository;
    }

    /**
     * Проверяет выполнение условий рекомендации.
     *
     * @param userId идентификатор пользователя
     * @return рекомендация, если правило выполнено,
     * иначе Optional.empty()
     */
    @Override
    public Optional<RecommendationDto> check(UUID userId) {

        boolean hasDebit =
                repository.hasProductType(
                        userId,
                        ProductType.DEBIT
                );


        BigDecimal savingDeposit =
                repository.getDepositSum(
                        userId,
                        ProductType.SAVING
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


        boolean has50kSaving =
                savingDeposit.compareTo(BigDecimal.valueOf(50000)) >= 0;


        boolean has50kDebit =
                debitDeposit.compareTo(BigDecimal.valueOf(50000)) >= 0;


        boolean depositMoreThanWithdraw =
                debitDeposit.compareTo(debitWithdraw) > 0;


        if (hasDebit
                && (has50kSaving || has50kDebit)
                && depositMoreThanWithdraw) {

            return Optional.of(
                    new RecommendationDto(
                            null,
                            PRODUCT_ID,
                            "Топ накопление",
                            """
                                    Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
                                    
                                    Преимущества «Копилки»:
                                    
                                    Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.
                                    
                                    Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.
                                    
                                    Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.
                                    
                                    Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!
                            """
                    )
            );
        }

        return Optional.empty();
    }
}