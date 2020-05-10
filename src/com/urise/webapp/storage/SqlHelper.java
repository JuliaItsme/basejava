package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
    public ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T sqlHelp(String sql, Executor<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            return executor.executor(ps, rs, sql);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface Executor<T> {
        T executor(PreparedStatement ps, ResultSet rs, String sql) throws SQLException;
    }

    public void sqlHelps(String sql, Executed executed) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            executed.executed(ps, sql);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface Executed {
        void executed(PreparedStatement ps, String sql) throws SQLException;
    }
}
