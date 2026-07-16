package ru.starbank.recommendation_service1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.starbank.recommendation_service1.service.RecommendationService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private RecommendationService service;



    @Test
    void shouldReturnRecommendations() throws Exception {

        UUID userId = UUID.randomUUID();


        when(service.getRecommendations(userId))
                .thenReturn(List.of());


        mockMvc.perform(
                        get("/recommendation/" + userId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id")
                        .value(userId.toString()))
                .andExpect(jsonPath("$.recommendations")
                        .isArray());
    }
}