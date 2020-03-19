package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStream;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectStream()));
    }
}