package ru.starbank.recommendation_service1.repository;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public class RecommendationRepository {


    private final JdbcTemplate jdbcTemplate;


    private final Cache<String, Boolean> userOfCache;
    private final Cache<String, Integer> activeUserCache;
    private final Cache<String, BigDecimal> transactionSumCache;


    public RecommendationRepository(
            @Qualifier("knowledgeJdbcTemplate")
            JdbcTemplate jdbcTemplate,

            Cache<String, Boolean> userOfCache,

            Cache<String, Integer> activeUserCache,

            Cache<String, BigDecimal> transactionSumCache
    ) {

        this.jdbcTemplate = jdbcTemplate;
        this.userOfCache = userOfCache;
        this.activeUserCache = activeUserCache;
        this.transactionSumCache = transactionSumCache;
    }



    /**
     * USER_OF
     */
    public boolean hasProductType(
            UUID userId,
            String productType
    ) {


        String key = userId + "_" + productType;


        return userOfCache.get(
                key,
                k -> {

                    String sql = """
                    SELECT COUNT(*) > 0
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p
                        ON t.PRODUCT_ID = p.ID
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
        );
    }



    /**
     * ACTIVE_USER_OF
     */
    public int countProducts(
            UUID userId,
            String productType
    ) {


        String key = userId + "_" + productType;


        return activeUserCache.get(
                key,
                k -> {


                    String sql = """
                    SELECT COUNT(*)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p
                        ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ?
                    AND p.TYPE = ?
                    """;


                    Integer count =
                            jdbcTemplate.queryForObject(
                                    sql,
                                    Integer.class,
                                    userId,
                                    productType
                            );


                    return count == null ? 0 : count;
                }
        );
    }




    /**
     * TRANSACTION_SUM_COMPARE
     */
    public BigDecimal getTransactionSum(
            UUID userId,
            String productType,
            String transactionType
    ) {


        String key =
                userId + "_" +
                        productType + "_" +
                        transactionType;



        return transactionSumCache.get(
                key,
                k -> {


                    String sql = """
                    SELECT COALESCE(SUM(t.AMOUNT),0)
                    FROM TRANSACTIONS t
                    JOIN PRODUCTS p
                        ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ?
                    AND p.TYPE = ?
                    AND t.TYPE = ?
                    """;


                    BigDecimal result =
                            jdbcTemplate.queryForObject(
                                    sql,
                                    BigDecimal.class,
                                    userId,
                                    productType,
                                    transactionType
                            );


                    return result == null
                            ? BigDecimal.ZERO
                            : result;
                }
        );
    }



    /**
     * TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW
     */

    public BigDecimal getDepositSum(
            UUID userId,
            String productType
    ) {

        return getTransactionSum(
                userId,
                productType,
                "DEPOSIT"
        );
    }



    public BigDecimal getWithdrawSum(
            UUID userId,
            String productType
    ) {

        return getTransactionSum(
                userId,
                productType,
                "WITHDRAW"
        );
    }

}