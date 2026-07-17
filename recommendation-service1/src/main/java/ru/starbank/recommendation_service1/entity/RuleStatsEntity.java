package ru.starbank.recommendation_service1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rule_stats")
@Getter
@Setter
public class RuleStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rule_id",
            nullable = false,
            unique = true
    )
    private RuleEntity rule;

    @Column(
            nullable = false
    )
    private Integer count = 0;
}