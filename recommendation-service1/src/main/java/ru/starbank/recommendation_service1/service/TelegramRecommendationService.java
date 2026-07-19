package ru.starbank.recommendation_service1.service;

import org.springframework.stereotype.Service;
import ru.starbank.recommendation_service1.entity.UserEntity;
import ru.starbank.recommendation_service1.repository.UserRepository;
import ru.starbank.recommendation_service1.dto.RecommendationDto;

import java.util.List;


@Service
public class TelegramRecommendationService {

    private final UserRepository userRepository;

    private final RecommendationService recommendationService;

    public TelegramRecommendationService(
            UserRepository userRepository,
            RecommendationService recommendationService
    ) {
        this.userRepository = userRepository;
        this.recommendationService = recommendationService;
    }

    public String recommend(String username) {

        List<UserEntity> users =
                userRepository.findByUsername(username);

        if (users.size() != 1) {

            return "Пользователь не найден";
        }

        UserEntity user = users.get(0);

        List<RecommendationDto> recommendations =
                recommendationService.getRecommendations(
                        user.getId()
                );

        StringBuilder response = new StringBuilder();

        response.append("Здравствуйте ")
                .append(user.getFirstName())
                .append(" ")
                .append(user.getLastName())
                .append("\n\n");

        response.append("Новые продукты для вас:\n");


        recommendations.forEach(
                recommendation ->
                        response.append("- ")
                                .append(recommendation.getProductName())
                                .append("\n")
        );

        return response.toString();
    }
}