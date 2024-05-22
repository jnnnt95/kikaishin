package com.nniett.kikaishin.app.persistence.repository.provider.database;

import com.nniett.kikaishin.common.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

public class ReadProvider {
    private static final Logger logger = LoggerFactory.getLogger(ReadProvider.class);

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
        logger.info("ReadProvider instantiated.");
    }

    public synchronized void runSelect(Connection connection) {
        logger.debug("Running select operation.");
        logger.trace("Query used for select operation: {}.", this.query);
        logger.trace("Query parameters for select operation: {}.", this.parameters);
        Action prepareAction = this.parameters.get(PREPARE);
        Action finishAction = this.parameters.get(FINISH);
        if(prepareAction != null && finishAction != null) {
            logger.debug("Trying auto-closable connection.");
            try (Connection conn = connection) {
                logger.debug("Trying auto-closable prepared statement.");
                try (PreparedStatement stmt = conn.prepareStatement(this.query)) {
                    prepareAction.execute(stmt);
                    logger.debug("Trying auto-closable result set.");
                    try (ResultSet result = stmt.executeQuery()) {
                        finishAction.execute(result);
                    }
                }
            } catch (SQLException e) {
                logger.error("Could not complete run select operation. Reason: {}.", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            logger.error("Could not complete run select operation. Reason: either prepared action or finish action is null.");
            throw new RuntimeException("Preparation or finishing action not provided.");
        }

    }
}
