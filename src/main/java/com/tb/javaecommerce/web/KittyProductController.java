package com.tb.javaecommerce.web;

import com.tb.javaecommerce.domain.KittyProduct;
import com.tb.javaecommerce.featuretoggle.FeatureToggles;
import com.tb.javaecommerce.featuretoggle.annotation.FeatureToggle;
import com.tb.javaecommerce.service.KittyProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kitty-products")
public class KittyProductController {
    private final KittyProductService kittyProductService;

    public KittyProductController(KittyProductService kittyProductService) {
        this.kittyProductService = kittyProductService;
    }

    @GetMapping
    @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    public ResponseEntity<List<KittyProduct>> getAllKittyProducts() {
        return ResponseEntity.ok(kittyProductService.getAllKittyProducts());
    }
}
