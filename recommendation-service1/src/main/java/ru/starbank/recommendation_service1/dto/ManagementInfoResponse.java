package ru.starbank.recommendation_service1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManagementInfoResponse {

    private String name;

    private String version;
}