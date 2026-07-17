package ru.starbank.recommendation_service1.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RecommendationDto {

    private Long id;

    private UUID productId;

    private String productName;

    private String productText;

}