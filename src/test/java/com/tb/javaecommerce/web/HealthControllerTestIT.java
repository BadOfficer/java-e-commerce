package com.tb.javaecommerce.web;

import com.tb.javaecommerce.service.HealthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
class HealthControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthService healthService;

    @Test
    void shouldReturn200WhenDatabaseIsAvailable() throws Exception {
        Mockito.when(healthService.isHealthy()).thenReturn(true);

        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn503WhenDatabaseIsUnavailable() throws Exception {
        Mockito.when(healthService.isHealthy()).thenReturn(false);

        mockMvc.perform(get("/health"))
                .andExpect(status().isServiceUnavailable());
    }
}
