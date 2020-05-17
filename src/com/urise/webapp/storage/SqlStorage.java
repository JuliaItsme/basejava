package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContact(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(
                conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid =?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, resume.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    try (PreparedStatement pst = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ? ")) {
                        pst.setString(1, resume.getUuid());
                        pst.execute();
                    }

                    insertContact(resume, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r" +
                        " LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        " WHERE r.uuid =? ",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    addContact(resume, rs);
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("" +
                        "DELETE FROM resume WHERE uuid = ?",
                (ps) -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("" +
                        "SELECT count(*) FROM resume",
                (ps) -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> execute = sqlHelper.execute("" +
                        "SELECT * FROM resume r" +
                        " LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        " ORDER BY full_name, uuid, id",
                (ps) -> {
                    List<Resume> list = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        addContact(resume, rs);
                        list.add(resume);
                    }
                    return list;
                });
        return execute;
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume ");
        sqlHelper.execute("ALTER SEQUENCE contact_id_seq RESTART WITH 1");
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        do {
            String type = rs.getString("type");
            if (type == null) break;
            ContactType contactType = ContactType.valueOf(type);
            String value = rs.getString("value");
            resume.addContact(contactType, value);
        } while (rs.next());
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> element : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, element.getKey().name());
                ps.setString(3, element.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}