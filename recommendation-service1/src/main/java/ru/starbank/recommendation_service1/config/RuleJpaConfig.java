package ru.starbank.recommendation_service1.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(
        basePackages =
                "ru.starbank.recommendation_service1.repository.rule",
        entityManagerFactoryRef =
                "ruleEntityManagerFactory",
        transactionManagerRef =
                "ruleTransactionManager"
)
public class RuleJpaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean ruleEntityManagerFactory(
            EntityManagerFactoryBuilder builder,

            @Qualifier("ruleDataSource")
            DataSource ruleDataSource
    ) {
        return builder
                .dataSource(ruleDataSource)
                .packages(
                        "ru.starbank.recommendation_service1.entity"
                )
                .persistenceUnit("rules")
                .build();
    }

    @Bean
    public PlatformTransactionManager ruleTransactionManager(
            @Qualifier("ruleEntityManagerFactory")
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(
                entityManagerFactory
        );
    }
}