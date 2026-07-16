package ru.starbank.recommendation_service1.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;


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

}