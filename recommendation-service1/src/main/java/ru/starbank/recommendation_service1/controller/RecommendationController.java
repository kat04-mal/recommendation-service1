package ru.starbank.recommendation_service1.controller;

import org.springframework.web.bind.annotation.*;
import ru.starbank.recommendation_service1.dto.RecommendationResponse;
import ru.starbank.recommendation_service1.service.RecommendationService;

import java.util.UUID;
/**
 * REST-контроллер для получения персональных рекомендаций пользователя.
 *
 * <p>Принимает идентификатор пользователя и возвращает список
 * подходящих банковских продуктов.</p>
 */
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {


    private final RecommendationService service;


    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    /**
     * Получение рекомендаций пользователя.
     *
     * @param userId идентификатор пользователя
     * @return объект с идентификатором пользователя и списком рекомендаций
     */

    @GetMapping("/{userId}")
    public RecommendationResponse getRecommendations(
            @PathVariable UUID userId
    ) {

        return new RecommendationResponse(
                userId,
                service.getRecommendations(userId)
        );
    }
}