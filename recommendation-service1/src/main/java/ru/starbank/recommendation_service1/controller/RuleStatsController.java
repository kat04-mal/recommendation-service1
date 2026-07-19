package ru.starbank.recommendation_service1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.starbank.recommendation_service1.dto.rule.RuleStatsResponse;
import ru.starbank.recommendation_service1.service.RuleStatsService;

/**
 * Контроллер статистики срабатывания правил рекомендаций.
 */
@RestController
@RequestMapping("/rule")
public class RuleStatsController {

    private final RuleStatsService service;

    public RuleStatsController(
            RuleStatsService service
    ) {
        this.service = service;
    }


    /**
     * Получение статистики применения правил.
     *
     * @return количество срабатываний каждого правила
     */
    @GetMapping("/stats")
    public ResponseEntity<RuleStatsResponse> getStats() {

        return ResponseEntity.ok(
                service.getStats()
        );
    }
}