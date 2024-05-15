package com.nniett.kikaishin.app.persistence.repository.provider.database;

import com.nniett.kikaishin.common.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class ReadProvider {

    public static final String PREPARE;
    public static final String FINISH;

    static {
        PREPARE = "prepare";
        FINISH = "finish";
    }

    private final String query;
    private final Map<String, Action> parameters;

    public ReadProvider(String query, Map<String, Action> parameters) {
        this.query = query;
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    public synchronized void runSelect(Connection connection) {
        Action prepareAction = this.parameters.get(PREPARE);
        Action finishAction = this.parameters.get(FINISH);
        if(prepareAction != null && finishAction != null) {
            try (Connection conn = connection) {
                try (PreparedStatement stmt = conn.prepareStatement(this.query)) {
                    prepareAction.execute(stmt);
                    try (ResultSet result = stmt.executeQuery()) {
                        finishAction.execute(result);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Preparation or finishing action not provided.");
        }

    }
}
