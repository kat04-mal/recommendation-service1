package ru.starbank.recommendation_service1.mapper;

import org.springframework.stereotype.Component;
import ru.starbank.recommendation_service1.dto.rule.RuleQueryDto;
import ru.starbank.recommendation_service1.dto.rule.RuleResponse;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.entity.RuleQueryEntity;

import java.util.List;

@Component
public class RuleMapper {


    public RuleResponse toResponse(RuleEntity entity) {

        List<RuleQueryDto> queries =
                entity.getRule()
                        .stream()
                        .map(this::toDto)
                        .toList();

        return new RuleResponse(
                entity.getId(),
                entity.getProductName(),
                entity.getProductId(),
                entity.getProductText(),
                queries
        );
    }

    private RuleQueryDto toDto(RuleQueryEntity entity) {

        RuleQueryDto dto = new RuleQueryDto();

        dto.setQuery(entity.getQuery());
        dto.setArguments(entity.getArguments());
        dto.setNegate(entity.isNegate());

        return dto;
    }
}