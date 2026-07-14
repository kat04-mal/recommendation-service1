package ru.starbank.recommendation_service1.dto;

import java.util.UUID;

public class RecommendationDto {

    private UUID id;
    private String name;
    private String text;


    public RecommendationDto() {
    }


    public RecommendationDto(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }


    public UUID getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getText() {
        return text;
    }
}