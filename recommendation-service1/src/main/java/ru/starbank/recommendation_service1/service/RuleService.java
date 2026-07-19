package ru.starbank.recommendation_service1.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.starbank.recommendation_service1.entity.RuleStatsEntity;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;
import ru.starbank.recommendation_service1.dto.rule.RuleCreateRequest;
import ru.starbank.recommendation_service1.dto.rule.RuleListResponse;
import ru.starbank.recommendation_service1.dto.rule.RuleResponse;
import ru.starbank.recommendation_service1.dto.rule.RuleQueryDto;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.entity.RuleQueryEntity;
import ru.starbank.recommendation_service1.mapper.RuleMapper;

import java.util.ArrayList;

/**
 * Сервис управления динамическими правилами.
 *
 * <p>Отвечает за создание, получение и удаление правил.</p>
 */
@Service
public class RuleService {

    private final RuleRepository repository;

    private final RuleMapper mapper;

    public RuleService(
            RuleRepository repository,
            RuleMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Создает новое правило рекомендации.
     *
     * @param request данные правила
     * @return сохраненное правило
     */
    @Transactional
    public RuleResponse createRule(RuleCreateRequest request) {

        RuleEntity rule = new RuleEntity();

        rule.setProductName(request.getProductName());

        rule.setProductId(request.getProductId());

        rule.setProductText(request.getProductText());

        var queries = new ArrayList<RuleQueryEntity>();


        for (RuleQueryDto dto : request.getRule()) {
            RuleQueryEntity query = new RuleQueryEntity();
            query.setRule(rule);
            query.setQuery(dto.getQuery());
            query.setArguments(dto.getArguments());
            query.setNegate(dto.isNegate());
            queries.add(query);
        }

        rule.setRule(queries);

        RuleStatsEntity stats = new RuleStatsEntity();

        stats.setRule(rule);
        stats.setCount(0);

        rule.setStats(stats);

        RuleEntity saved =
                repository.save(rule);

        return mapper.toResponse(saved);
    }

    /**
     * Возвращает все существующие правила.
     *
     * @return список правил
     */
    @Transactional(readOnly = true)
    public RuleListResponse getRules() {

        return new RuleListResponse(
                repository.findAll()
                        .stream()
                        .map(mapper::toResponse)
                        .toList()
        );
    }

    /**
     * Удаляет правило по идентификатору.
     *
     * @param id идентификатор правила
     */
    @Transactional
    public void deleteRule(Long id) {

        RuleEntity rule =
                repository.findById(id)
                        .orElseThrow();
        repository.delete(rule);
    }
}