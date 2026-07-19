package ru.starbank.recommendation_service1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.info.BuildProperties;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.starbank.recommendation_service1.service.RecommendationService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ManagementControllerTest {


    @Test
    void shouldClearCaches() throws Exception {

        RecommendationService recommendationService =
                mock(RecommendationService.class);


        BuildProperties buildProperties =
                mock(BuildProperties.class);


        ManagementController controller =
                new ManagementController(
                        recommendationService,
                        buildProperties
                );


        MockMvc mockMvc =
                MockMvcBuilders
                        .standaloneSetup(controller)
                        .build();


        mockMvc.perform(
                        post("/management/clear-caches")
                )
                .andExpect(
                        status().isOk()
                );


        verify(
                recommendationService,
                times(1)
        )
                .clearCache();
    }


    @Test
    void shouldReturnServiceInfo() throws Exception {


        RecommendationService recommendationService =
                mock(RecommendationService.class);


        BuildProperties buildProperties =
                mock(BuildProperties.class);


        when(buildProperties.getArtifact())
                .thenReturn("recommendation-service1");


        when(buildProperties.getVersion())
                .thenReturn("0.0.1-SNAPSHOT");


        ManagementController controller =
                new ManagementController(
                        recommendationService,
                        buildProperties
                );

        MockMvc mockMvc =
                MockMvcBuilders
                        .standaloneSetup(controller)
                        .build();

        mockMvc.perform(
                        get("/management/info")
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("recommendation-service1")
                )
                .andExpect(
                        jsonPath("$.version")
                                .value("0.0.1-SNAPSHOT")
                );
    }
}