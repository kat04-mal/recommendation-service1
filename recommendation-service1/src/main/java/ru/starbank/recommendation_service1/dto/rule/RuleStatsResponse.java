package ru.starbank.recommendation_service1.dto.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RuleStatsResponse {

    private List<RuleStatsDto> stats;
}