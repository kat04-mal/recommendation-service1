package ru.starbank.recommendation_service1.dto.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RuleQueryDto {

    private String query;

    private List<String> arguments;

    private boolean negate;
}