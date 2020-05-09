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

    public <T> T sqlHelp(String sql, SqlHelp<T> sqlHelp) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            return sqlHelp.execute(ps, rs);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SqlHelp<T> {
        T execute(PreparedStatement ps, ResultSet rs) throws SQLException;
    }

    public void sqlHelps(String sql, SqlHelps sqlHelps) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            sqlHelps.executes(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SqlHelps {
        void executes(PreparedStatement ps) throws SQLException;
    }
}
