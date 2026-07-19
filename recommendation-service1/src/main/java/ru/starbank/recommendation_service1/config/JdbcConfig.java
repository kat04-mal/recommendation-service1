package ru.starbank.recommendation_service1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class JdbcConfig {

    @Bean(name = "knowledgeJdbcTemplate")
    public JdbcTemplate knowledgeJdbcTemplate(
            DataSource defaultDataSource
    ) {
        return new JdbcTemplate(defaultDataSource);
    }
}