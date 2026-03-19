package com.tb.javaecommerce.web;

import com.tb.javaecommerce.AbstractIt;
import com.tb.javaecommerce.featuretoggle.FeatureToggleExtension;
import com.tb.javaecommerce.featuretoggle.FeatureToggles;
import com.tb.javaecommerce.featuretoggle.annotation.DisabledFeatureToggle;
import com.tb.javaecommerce.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(FeatureToggleExtension.class)
@SpringBootTest
public class KittyProductControllerTestIT extends AbstractIt {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet404KittyProductDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/kitty-products")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    void shouldGet200KittyProductEnabled() throws Exception {
        mockMvc.perform(get("/api/v1/kitty-products")).andExpect(status().isOk());
    }
}
