package ru.starbank.recommendation_service1.repository.rule;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.starbank.recommendation_service1.entity.RuleStatsEntity;

public interface RuleStatsRepository extends JpaRepository<RuleStatsEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select s
            from RuleStatsEntity s
            where s.rule.id = :ruleId
            """)
    RuleStatsEntity findForUpdate(
            @Param("ruleId") Long ruleId
    );
}