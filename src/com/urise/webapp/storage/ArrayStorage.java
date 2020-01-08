package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Error. Resume don't found.");
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) != -1) {
            System.out.println("Error. There is such resume already.");
        } else if (size == STORAGE_LIMIT) {
            System.out.println("Error. The storage is full.");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Error. Resume don't found.");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}