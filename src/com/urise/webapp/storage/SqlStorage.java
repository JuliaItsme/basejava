package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
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
                    insertContacts(resume, connection);
                    insertSections(resume, connection);
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
                    insertContacts(resume, connection);
                    insertSections(resume, connection);
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
                            getContact(resume, resultSet);
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT type, value FROM section  WHERE resume_uuid =?")) {
                        preparedStatement.setString(1, uuid);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            getSection(resume, resultSet);
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
                            map.putIfAbsent(uuid, new Resume(uuid, resultSet.getString("full_name")));
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contact ORDER BY id")) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            getContact(map.get(resultSet.getString("resume_uuid")), resultSet);
                        }
                    }
                    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM section ORDER BY id")) {
                        ResultSet resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            getSection(map.get(resultSet.getString("resume_uuid")), resultSet);
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
        sqlHelper.execute("DELETE FROM resume; ALTER SEQUENCE contact_id_seq RESTART WITH 1;ALTER SEQUENCE section_id_seq RESTART WITH 1 ");
    }

    private void deleteType(Resume resume, Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
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

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> element : resume.getSections().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, element.getKey().name());
                preparedStatement.setString(3, JsonParser.write(element.getValue(), Section.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void getContact(Resume resume, ResultSet resultSet) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            resume.setContact(ContactType.valueOf(resultSet.getString("type")), value);
        }
    }

    private void getSection(Resume resume, ResultSet resultSet) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
            resume.setSection(sectionType, JsonParser.read(value, Section.class));
        }
    }

/*    private String addSection(SectionType sectionType, Section section) throws SQLException {
        if (section != null) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    return ((TextSection) section).getContent();
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    return String.join("/n", ((ListSection) section).getItems());
            }
        }
        return null;
    }

    private void getSection(Resume resume, ResultSet resultSet) throws SQLException {
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
                    String[] array = value.split("/n", 0);
                    resume.addSection(sectionType, new ListSection(new ArrayList<>(Arrays.asList(array))));
                    break;
            }
        }
    } */
}