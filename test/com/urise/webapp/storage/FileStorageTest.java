package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStream;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStream()));
    }
}