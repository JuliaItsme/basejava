package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Error. Resume don't found.");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Error. Resume don't found.");
            return null;
        }
        return storage[index];
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            System.out.print("Error. There is such resume already.");
        } else if (size == STORAGE_LIMIT) {
            System.out.println("Error. The storage is full.");
        } else {
            saveResume(resume, index);
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index <= -1) {
            System.out.println("Error. Resume don't found.");
        } else {
            deleteResume(index);
            size--;
        }
    }

    protected abstract int getIndex(String uuid);
    protected abstract void saveResume(Resume resume, int index);
    protected abstract void deleteResume(int index);
}
