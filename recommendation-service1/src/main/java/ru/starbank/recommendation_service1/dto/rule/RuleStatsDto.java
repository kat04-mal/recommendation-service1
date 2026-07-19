package ru.starbank.recommendation_service1.dto.rule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RuleStatsDto {

    @JsonProperty("rule_id")
    private Long ruleId;

    private Integer count;
}