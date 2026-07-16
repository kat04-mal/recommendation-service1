package ru.starbank.recommendation_service1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.starbank.recommendation_service1.dto.rule.RuleCreateRequest;
import ru.starbank.recommendation_service1.dto.rule.RuleListResponse;
import ru.starbank.recommendation_service1.dto.rule.RuleResponse;
import ru.starbank.recommendation_service1.service.RuleService;


@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RuleResponse> createRule(
            @RequestBody RuleCreateRequest request
    ) {

        return ResponseEntity.ok(
                service.createRule(request)
        );
    }

    @GetMapping
    public ResponseEntity<RuleListResponse> getRules() {

        return ResponseEntity.ok(
                service.getRules()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(
            @PathVariable("id") Long id
    ) {

        service.deleteRule(id);

        return ResponseEntity.noContent().build();
    }
}