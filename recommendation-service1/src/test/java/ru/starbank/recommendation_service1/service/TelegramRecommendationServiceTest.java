package ru.starbank.recommendation_service1.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.starbank.recommendation_service1.dto.RecommendationDto;
import ru.starbank.recommendation_service1.entity.UserEntity;
import ru.starbank.recommendation_service1.repository.UserRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


class TelegramRecommendationServiceTest {


    @Test
    void shouldReturnRecommendationsForExistingUser() {

        UserRepository repository =
                Mockito.mock(UserRepository.class);

        RecommendationService recommendationService =
                Mockito.mock(RecommendationService.class);


        UUID userId = UUID.randomUUID();


        UserEntity user = new UserEntity();

        user.setId(userId);
        user.setUsername("ivan");
        user.setFirstName("Иван");
        user.setLastName("Иванов");


        when(repository.findByUsername("ivan"))
                .thenReturn(List.of(user));


        when(recommendationService.getRecommendations(userId))
                .thenReturn(
                        List.of(
                                new RecommendationDto(
                                        1L,
                                        UUID.randomUUID(),
                                        "Кредитная карта",
                                        "Описание"
                                )
                        )
                );


        TelegramRecommendationService service =
                new TelegramRecommendationService(
                        repository,
                        recommendationService
                );


        String result =
                service.recommend("ivan");


        assertTrue(
                result.contains("Здравствуйте Иван Иванов")
        );

        assertTrue(
                result.contains("Новые продукты для вас:")
        );

        assertTrue(
                result.contains("- Кредитная карта")
        );
    }


    @Test
    void shouldReturnUserNotFoundWhenUserDoesNotExist() {


        UserRepository repository =
                Mockito.mock(UserRepository.class);


        RecommendationService recommendationService =
                Mockito.mock(RecommendationService.class);


        when(repository.findByUsername("unknown"))
                .thenReturn(List.of());


        TelegramRecommendationService service =
                new TelegramRecommendationService(
                        repository,
                        recommendationService
                );


        String result =
                service.recommend("unknown");


        assertTrue(
                result.equals("Пользователь не найден")
        );
    }



    @Test
    void shouldReturnUserNotFoundWhenMultipleUsersFound() {


        UserRepository repository =
                Mockito.mock(UserRepository.class);


        RecommendationService recommendationService =
                Mockito.mock(RecommendationService.class);


        UserEntity first =
                new UserEntity();

        UserEntity second =
                new UserEntity();


        when(repository.findByUsername("ivan"))
                .thenReturn(
                        List.of(first, second)
                );


        TelegramRecommendationService service =
                new TelegramRecommendationService(
                        repository,
                        recommendationService
                );


        String result =
                service.recommend("ivan");


        assertTrue(
                result.equals("Пользователь не найден")
        );
    }
}
