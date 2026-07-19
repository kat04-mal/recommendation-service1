package ru.starbank.recommendation_service1.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.starbank.recommendation_service1.dto.RecommendationDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Конфигурация кешей приложения.
 *
 * <p>Создает кеш результатов рекомендаций
 * и промежуточных вычислений правил.</p>
 */
@Configuration
public class CacheConfig {


    @Bean
    public Cache<String, Boolean> userOfCache() {
        return Caffeine.newBuilder()
                .build();
    }


    @Bean
    public Cache<String, Integer> activeUserCache() {
        return Caffeine.newBuilder()
                .build();
    }


    @Bean
    public Cache<String, BigDecimal> transactionSumCache() {
        return Caffeine.newBuilder()
                .build();
    }


    /**
     * Кеш результатов рекомендаций.
     */
    @Bean
    public Cache<UUID, List<RecommendationDto>> recommendationCache() {
        return Caffeine.newBuilder()
                .build();
    }
}