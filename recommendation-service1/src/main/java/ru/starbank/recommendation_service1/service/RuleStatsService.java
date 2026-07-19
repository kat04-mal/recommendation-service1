package ru.starbank.recommendation_service1.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starbank.recommendation_service1.dto.rule.RuleStatsDto;
import ru.starbank.recommendation_service1.dto.rule.RuleStatsResponse;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.entity.RuleStatsEntity;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;
import ru.starbank.recommendation_service1.repository.rule.RuleStatsRepository;

import java.util.List;

/**
 * Сервис формирования статистики срабатывания правил.
 */
@Service
public class RuleStatsService {

    private final RuleStatsRepository statsRepository;

    private final RuleRepository ruleRepository;

    public RuleStatsService(
            RuleStatsRepository statsRepository,
            RuleRepository ruleRepository
    ) {
        this.statsRepository = statsRepository;
        this.ruleRepository = ruleRepository;
    }

    @Transactional
    public void increment(
            RuleEntity rule
    ) {
        RuleStatsEntity stats =
                statsRepository.findForUpdate(
                        rule.getId()
                );

        if (stats == null) {
            stats = new RuleStatsEntity();
            stats.setRule(rule);
            stats.setCount(1);
        } else {
            stats.setCount(
                    stats.getCount() + 1
            );
        }
        statsRepository.save(stats);
    }


    /**
     * Возвращает количество срабатываний каждого правила.
     *
     * @return статистика правил
     */
    @Transactional(readOnly = true)
    public RuleStatsResponse getStats() {
        List<RuleStatsDto> result =
                ruleRepository.findAll()
                        .stream()
                        .map(rule -> {
                            int count = 0;
                            if (rule.getStats() != null) {
                                count =
                                        rule.getStats()
                                                .getCount();
                            }
                            return new RuleStatsDto(
                                    rule.getId(),
                                    count
                            );
                        })
                        .toList();
        return new RuleStatsResponse(result);
    }
}