package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.repository.provider.database.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

@Repository
public abstract class VirtualRepository<T> {

    private final ConnectionProvider connectionProvider;

    @Autowired
    public VirtualRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    protected Connection getConnection() {
        return this.connectionProvider.get();
    }

    protected abstract T getInstance();

}
