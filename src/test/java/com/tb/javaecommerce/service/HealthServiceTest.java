package com.tb.javaecommerce.service;

import com.tb.javaecommerce.service.impl.HealthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HealthServiceTest {

    @Test
    void shouldReturnTrueWhenDatabaseConnectionIsValid() throws SQLException {
        DataSource dataSource = Mockito.mock(DataSource.class);
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        Mockito.when(connection.isValid(2)).thenReturn(true);

        HealthService healthService = new HealthServiceImpl(dataSource, 2);

        assertTrue(healthService.isHealthy());
        Mockito.verify(connection).close();
    }

    @Test
    void shouldReturnFalseWhenDatabaseConnectionFails() throws SQLException {
        DataSource dataSource = Mockito.mock(DataSource.class);
        Mockito.when(dataSource.getConnection()).thenThrow(new SQLException("DB unavailable"));

        HealthService healthService = new HealthServiceImpl(dataSource, 2);

        assertFalse(healthService.isHealthy());
    }
}
