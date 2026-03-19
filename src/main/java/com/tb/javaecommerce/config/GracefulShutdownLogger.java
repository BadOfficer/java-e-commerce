package com.tb.javaecommerce.config;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GracefulShutdownLogger {
    @PreDestroy
    public void onContextClosed() {
        log.info("SIGTERM received. Starting graceful shutdown...");
    }
}
