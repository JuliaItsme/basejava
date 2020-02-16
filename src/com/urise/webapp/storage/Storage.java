package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public interface Storage {

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    Resume[] getAll();

    void delete(String uuid);

    void clear();

    int size();
}