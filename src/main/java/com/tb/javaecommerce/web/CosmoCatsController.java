package com.tb.javaecommerce.web;

import com.tb.javaecommerce.domain.Category;
import com.tb.javaecommerce.domain.CosmoCat;
import com.tb.javaecommerce.domain.KittyProduct;
import com.tb.javaecommerce.featuretoggle.FeatureToggles;
import com.tb.javaecommerce.featuretoggle.annotation.FeatureToggle;
import com.tb.javaecommerce.service.CosmoCatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatsController {
    private final CosmoCatsService cosmoCatsService;

    public CosmoCatsController(CosmoCatsService cosmoCatsService) {
        this.cosmoCatsService = cosmoCatsService;
    }

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<CosmoCat>> getCosmoCats() {
        return ResponseEntity.ok(cosmoCatsService.getCosmoCats());
    }
}
