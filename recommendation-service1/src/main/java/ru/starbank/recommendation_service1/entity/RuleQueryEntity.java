package ru.starbank.recommendation_service1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "rule_query")
@Getter
@Setter
public class RuleQueryEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rule_id",
            nullable = false
    )
    private RuleEntity rule;

    @Column(nullable = false)
    private String query;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<String> arguments;

    @Column(nullable = false)
    private boolean negate;

}