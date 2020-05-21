package com.urise.webapp.storage;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ExceptionUtil;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

//TODO implement Section (except OrganizationSection)
//TODO join and split listSection by '\n'

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
                    deleteContact(resume, conn);
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
                    do {
                        addContact(resume, rs);
                    } while (rs.next());
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
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        String fullName = rs.getString("full_name");
                        //map.putIfAbsent(uuid,new Resume(uuid, rs.getString("full_name")));
                        addContact(map.computeIfAbsent(uuid, resume -> new Resume(uuid, fullName)), rs);
                    }
                    return new ArrayList<>(map.values());
                });
        return execute;
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume; ALTER SEQUENCE contact_id_seq RESTART WITH 1");
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void deleteContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ? ")) {
            pst.setString(1, resume.getUuid());
            pst.execute();
        }
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