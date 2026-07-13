package ru.starbank.recommendation_service1.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public class RecommendationRepository {

    private final JdbcTemplate jdbcTemplate;


    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    /**
     * Проверяем, есть ли у пользователя продукт определенного типа
     */
    public boolean hasProductType(UUID userId, String productType) {

        String sql = """
                SELECT COUNT(*) > 0
                FROM TRANSACTIONS t
                JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                WHERE t.USER_ID = ?
                  AND p.TYPE = ?
                """;


        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(
                        sql,
                        Boolean.class,
                        userId,
                        productType
                )
        );
    }


    /**
     * Сумма пополнений
     */
    public BigDecimal getDepositSum(UUID userId, String productType) {

        String sql = """
                SELECT COALESCE(SUM(t.AMOUNT), 0)
                FROM TRANSACTIONS t
                JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                WHERE t.USER_ID = ?
                  AND p.TYPE = ?
                  AND t.TYPE = 'DEPOSIT'
                """;


        return jdbcTemplate.queryForObject(
                sql,
                BigDecimal.class,
                userId,
                productType
        );
    }


    /**
     * Сумма списаний
     */
    public BigDecimal getWithdrawSum(UUID userId, String productType) {

        String sql = """
                SELECT COALESCE(SUM(t.AMOUNT), 0)
                FROM TRANSACTIONS t
                JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                WHERE t.USER_ID = ?
                  AND p.TYPE = ?
                  AND t.TYPE = 'WITHDRAW'
                """;


        return jdbcTemplate.queryForObject(
                sql,
                BigDecimal.class,
                userId,
                productType
        );
    }
}