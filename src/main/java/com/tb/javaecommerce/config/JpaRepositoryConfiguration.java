package com.tb.javaecommerce.config;

import com.tb.javaecommerce.repository.impl.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "com.tb.javaecommerce.repository",
        repositoryBaseClass = NaturalIdRepositoryImpl.class
)
public class JpaRepositoryConfiguration {
}
