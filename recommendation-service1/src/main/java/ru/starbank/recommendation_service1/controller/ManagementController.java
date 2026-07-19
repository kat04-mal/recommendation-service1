package ru.starbank.recommendation_service1.controller;

import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.starbank.recommendation_service1.dto.ManagementInfoResponse;
import ru.starbank.recommendation_service1.service.RecommendationService;


/**
 * Контроллер административного управления приложением.
 *
 * <p>Предоставляет операции очистки кешей и получения
 * информации о версии приложения.</p>
 */
@RestController
@RequestMapping("/management")
public class ManagementController {


    private final RecommendationService recommendationService;

    private final BuildProperties buildProperties;


    public ManagementController(
            RecommendationService recommendationService,
            BuildProperties buildProperties
    ) {

        this.recommendationService = recommendationService;
        this.buildProperties = buildProperties;
    }



    /**
     * Очистка кеша рекомендаций.
     *
     * @return HTTP статус 200 после успешной очистки
     */
    @PostMapping("/clear-caches")
    public ResponseEntity<Void> clearCaches() {

        recommendationService.clearCache();

        return ResponseEntity.ok().build();
    }



    /**
     * Получение информации о приложении.
     *
     * @return название приложения и версия из build.properties
     */
    @GetMapping("/info")
    public ResponseEntity<ManagementInfoResponse> info() {


        return ResponseEntity.ok(
                new ManagementInfoResponse(
                        buildProperties.getArtifact(),
                        buildProperties.getVersion()
                )
        );
    }
}