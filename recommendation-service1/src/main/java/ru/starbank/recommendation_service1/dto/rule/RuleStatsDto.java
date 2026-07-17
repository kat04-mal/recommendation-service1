package ru.starbank.recommendation_service1.dto.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RuleStatsDto {

    private Long rule_id;

    private Integer count;
}