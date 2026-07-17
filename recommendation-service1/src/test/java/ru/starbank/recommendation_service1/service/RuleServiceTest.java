package ru.starbank.recommendation_service1.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.starbank.recommendation_service1.entity.RuleEntity;
import ru.starbank.recommendation_service1.mapper.RuleMapper;
import ru.starbank.recommendation_service1.repository.rule.RuleRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RuleServiceTest {

    @Test
    void shouldDeleteRule() {

        RuleRepository repository =
                Mockito.mock(RuleRepository.class);

        RuleMapper mapper =
                Mockito.mock(RuleMapper.class);

        RuleEntity rule =
                new RuleEntity();

        rule.setId(5L);

        when(repository.findById(5L))
                .thenReturn(
                        Optional.of(rule)
                );


        RuleService service =
                new RuleService(
                        repository,
                        mapper
                );

        service.deleteRule(5L);

        verify(repository, times(1))
                .delete(rule);
    }
}