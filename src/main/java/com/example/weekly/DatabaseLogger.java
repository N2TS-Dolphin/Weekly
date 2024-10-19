package com.example.weekly;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLogger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DatabaseLogger.class);
    private final JdbcTemplate jdbcTemplate;

    public DatabaseLogger(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void logRequest(String uri, String method, int status) {
        String sql = "INSERT INTO api_logs (request_uri, request_method, response_status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, uri, method, status);
        logger.info("Logged request to database: URI={}, Method={}, Status={}", uri, method, status);
    }
}
