package ru.starbank.recommendation_service1.dto.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RuleCreateRequest {

    private String productName;

    private UUID productId;

    private String productText;

    private List<RuleQueryDto> rule;
}