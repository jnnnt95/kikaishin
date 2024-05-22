package com.nniett.kikaishin.app.persistence.repository.provider.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Component
public class ConnectionProvider {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionProvider.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ConnectionProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        logger.info("ConnectionProvider initialized.");
    }

    public Connection get() {
        logger.info("Connection object requested.");
        try {
            return Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
        } catch(SQLException | NullPointerException e) {
            logger.error("Connection object could not be retrieved. Reason: {}.", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
