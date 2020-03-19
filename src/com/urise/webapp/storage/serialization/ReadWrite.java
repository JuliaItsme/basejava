package com.urise.webapp.storage.serialization;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ReadWrite {

    void doWrite(Resume r, OutputStream file) throws IOException;

    Resume doRead(InputStream file) throws IOException;
}
