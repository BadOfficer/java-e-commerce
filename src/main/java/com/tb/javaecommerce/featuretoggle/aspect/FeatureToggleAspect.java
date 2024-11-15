package com.tb.javaecommerce.featuretoggle.aspect;

import com.tb.javaecommerce.featuretoggle.FeatureToggleService;
import com.tb.javaecommerce.featuretoggle.FeatureToggles;
import com.tb.javaecommerce.featuretoggle.annotation.FeatureToggle;
import com.tb.javaecommerce.featuretoggle.exception.FeatureNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Before(value = "@annotation(featureToggle)")
    public void checkFeatureToggle(FeatureToggle featureToggle){
        FeatureToggles toggle = featureToggle.value();

        if(!featureToggleService.checkFeatureToggle(toggle.getName())) {
            throw new FeatureNotEnabledException(toggle.getName());
        }
    }
}
