package ru.starbank.recommendation_service1.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.starbank.recommendation_service1.config.TelegramBotConfig;
import ru.starbank.recommendation_service1.service.TelegramRecommendationService;

@Component
public class RecommendationTelegramBot extends TelegramLongPollingBot {

    private final TelegramRecommendationService service;

    private final TelegramBotConfig config;

    public RecommendationTelegramBot(
            TelegramRecommendationService service,
            TelegramBotConfig config
    ) {
        super(config.getToken());
        this.service = service;
        this.config = config;
    }

    @Override
    public String getBotUsername() {

        return config.getName();
    }

    @Override
    public void onUpdateReceived(
            Update update
    ) {
        if (!update.hasMessage()
                || !update.getMessage().hasText()) {
            return;
        }
        String text =
                update.getMessage()
                        .getText()
                        .trim();

        String response;

        String[] parts =
                text.split("\\s+");

        if ("/start".equals(parts[0])) {

            response =
                    """
                    Добро пожаловать в StarBank Recommendation Bot.
        
                    Доступная команда:
        
                    /recommend username
        
                    Получить персональные рекомендации.
                    """;

        }
        else if ("/recommend".equals(parts[0])) {

            if (parts.length != 2) {

                response =
                        "Используйте команду:\n/recommend username";

            } else {

                response =
                        service.recommend(parts[1]);
            }

        }
        else {

            response =
                    "Неизвестная команда.\nИспользуйте /recommend username";
        }
        SendMessage message =
                new SendMessage();
        message.setChatId(
                update.getMessage()
                        .getChatId()
                        .toString()
        );
        message.setText(response);
        try {
            execute(message);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}