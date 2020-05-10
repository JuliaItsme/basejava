package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.sqlHelps("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps, sql) -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.sqlHelps("UPDATE resume SET full_name = ? WHERE uuid =?", (ps, sql) -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.execute();

        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.sqlHelp("SELECT * FROM resume r WHERE r.uuid =?", (ps, rs, sql) -> {
            ps.setString(1, uuid);
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.sqlHelps("DELETE FROM resume WHERE uuid = ?", (ps, sql) -> {
            ps.setString(1, uuid);
            ps.execute();
        });
    }

    @Override
    public int size() {
        return sqlHelper.sqlHelp("SELECT count(*) FROM resume", (ps, rs, sql) -> {
            int sum = 0;
            while (rs.next()) {
                sum = rs.getInt(1);
            }
            return sum;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.sqlHelp("SELECT * FROM resume ORDER BY uuid", (ps, rs, sql) -> {
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });
    }

    @Override
    public void clear() {
        sqlHelper.sqlHelps("DELETE FROM resume", (ps, sql) -> ps.execute());
    }
}
