package ru.starbank.recommendation_service1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.starbank.recommendation_service1.dto.rule.RuleStatsResponse;
import ru.starbank.recommendation_service1.service.RuleStatsService;

@RestController
@RequestMapping("/rule")
public class RuleStatsController {

    private final RuleStatsService service;

    public RuleStatsController(
            RuleStatsService service
    ) {
        this.service = service;
    }


    @GetMapping("/stats")
    public ResponseEntity<RuleStatsResponse> getStats() {

        return ResponseEntity.ok(
                service.getStats()
        );
    }
}