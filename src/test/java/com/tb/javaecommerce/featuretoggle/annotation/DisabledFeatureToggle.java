package com.tb.javaecommerce.featuretoggle.annotation;

import com.tb.javaecommerce.featuretoggle.FeatureToggles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DisabledFeatureToggle {
    FeatureToggles value();
}
