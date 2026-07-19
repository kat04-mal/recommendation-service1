package ru.starbank.recommendation_service1.dto.rule;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class RuleResponse {

    private final Long id;

    private final String productName;

    private final UUID productId;

    private final String productText;

    private final List<RuleQueryDto> rule;


    public RuleResponse(
            Long id,
            String productName,
            UUID productId,
            String productText,
            List<RuleQueryDto> rule
    ) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.rule = rule;
    }
}