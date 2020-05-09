package com.urise.webapp.storage;

public class SqlStorageTest extends AbstractStorageTest{
    public SqlStorageTest() {
        super(new SqlStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}