package com.urise.webapp.storage;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.ExceptionUtil;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.storage.serializer.DataStreamSerializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

//TODO join and split listSection by '\n'

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(
                connection -> {
                    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        preparedStatement.setString(1, resume.getUuid());
                        preparedStatement.setString(2, resume.getFullName());
                        preparedStatement.execute();
                    }
                    insertContact(resume, connection);
                    insertSection(resume, connection);
                    return null;
                }
        );
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(
                connection -> {
                    try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid =?")) {
                        preparedStatement.setString(1, resume.getFullName());
                        preparedStatement.setString(2, resume.getUuid());
                        if (preparedStatement.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    deleteType(resume, connection, "DELETE FROM contact WHERE resume_uuid = ? ");
                    deleteType(resume, connection, "DELETE FROM section WHERE resume_uuid = ? ");
                    insertContact(resume, connection);
                    insertSection(resume, connection);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(
                connection -> {
                    Resume resume;
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
                        preparedStatement.setString(1, uuid);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (!resultSet.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        resume = new Resume(uuid, resultSet.getString("full_name"));
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT type, value FROM contact  WHERE resume_uuid =?")) {
                        preparedStatement.setString(1, uuid);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            addContact(resume, resultSet);
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT type, value FROM section  WHERE resume_uuid =?")) {
                        preparedStatement.setString(1, uuid);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            addSection(resume, resultSet);
                        }
                    }
                    return resume;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(
                connection -> {
                    Map<String, Resume> map = new LinkedHashMap<>();
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            String uuid = resultSet.getString("uuid");
                            //String fullName = resultSet.getString("full_name");
                            map.putIfAbsent(uuid, new Resume(uuid, resultSet.getString("full_name")));
                            //map.computeIfAbsent(uuid, resume -> new Resume(uuid, fullName));
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact ORDER BY id")) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            addContact(map.get(resultSet.getString("resume_uuid")), resultSet);
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section ORDER BY id")) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            addSection(map.get(resultSet.getString("resume_uuid")), resultSet);
                        }
                    }
                    return new ArrayList<>(map.values());
                }
        );
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
    public void clear() {
        sqlHelper.execute("DELETE FROM resume; ALTER SEQUENCE contact_id_seq RESTART WITH 1");
    }

    private void addContact(Resume resume, ResultSet resultSet) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(resultSet.getString("type")), value);
        }
    }

    private void addSection(Resume resume, ResultSet resultSet) throws SQLException {
        String value = resultSet.getString("value");
        SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
        if (value != null) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    resume.addSection(sectionType, new ListSection(listAdd(sectionType, resultSet)));
                    break;
            }
        }
    }

    private List<String> listAdd(SectionType sectionType, ResultSet resultSet) throws SQLException {
        Map<SectionType, String> map = new LinkedHashMap<>();
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            map.put(SectionType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
        }
        for (Map.Entry<SectionType, String> element : map.entrySet()) {
            if (element.getKey().equals(sectionType)) {
                list.add(element.getValue());
            }
        }
        return list;
    }

    private void deleteType(Resume resume, Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void insertContact(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> element : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, element.getKey().name());
                preparedStatement.setString(3, element.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void insertSection(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> element : resume.getSections().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, element.getKey().name());
                preparedStatement.setString(3, element.getValue().toString());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}