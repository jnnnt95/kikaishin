package com.nniett.kikaishin.app.persistence.repository.virtual;

import com.nniett.kikaishin.app.persistence.repository.provider.database.ConnectionProvider;

import java.sql.Connection;

public abstract class VirtualRepository<T> {

    private final ConnectionProvider connectionProvider;

    public VirtualRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    protected Connection getConnection() {
        return this.connectionProvider.get();
    }

    protected abstract T getInstance();

}
