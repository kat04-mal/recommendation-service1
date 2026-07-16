package ru.starbank.recommendation_service1.dto.rule;

import lombok.Getter;

import java.util.List;

@Getter
public class RuleListResponse {

    private final List<RuleResponse> data;

    public RuleListResponse(List<RuleResponse> data) {
        this.data = data;
    }
}