package ru.starbank.recommendation_service1.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {


    /**
     * Основная БД.
     * Пользователи, продукты, транзакции.
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties defaultDataSourceProperties() {

        return new DataSourceProperties();
    }

    @Bean(name = "defaultDataSource")
    @Primary
    public DataSource defaultDataSource() {

        return defaultDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    /**
     * Вторая БД.
     * Динамические правила рекомендаций.
     */
    @Bean
    @ConfigurationProperties("rule.datasource")
    public DataSourceProperties ruleDataSourceProperties() {

        return new DataSourceProperties();
    }

    @Bean(name = "ruleDataSource")
    public DataSource ruleDataSource() {

        return ruleDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }
}
