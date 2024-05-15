package com.nniett.kikaishin.app.persistence.repository.provider.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

@Component
public class ConnectionProvider {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ConnectionProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Connection get() {
        try {
            return Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
        } catch(SQLException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
