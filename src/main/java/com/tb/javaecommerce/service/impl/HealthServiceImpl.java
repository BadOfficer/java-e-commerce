package com.tb.javaecommerce.service.impl;

import com.tb.javaecommerce.service.HealthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class HealthServiceImpl implements HealthService {
    private final DataSource dataSource;
    private final int dbTimeoutSeconds;

    public HealthServiceImpl(DataSource dataSource,
                             @Value("${app.health.db-timeout-seconds}") int dbTimeoutSeconds) {
        this.dataSource = dataSource;
        this.dbTimeoutSeconds = dbTimeoutSeconds;
    }

    @Override
    public boolean isHealthy() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(dbTimeoutSeconds);
        } catch (Exception ex) {
            return false;
        }
    }
}
