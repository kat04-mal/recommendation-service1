package ru.starbank.recommendation_service1.repository.rule;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.starbank.recommendation_service1.entity.RuleEntity;

public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
}