package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Error. The array is full.");
        } else if (checkArray(resume)) {
            storage[size] = resume;
            size++;

        }
    }

    private boolean checkArray(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                System.out.println("Error. There is such resume already.");
                return false;
            }
        }
        return true;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        storage[checkArray(uuid)] = resume;
    }

    public Resume get(String uuid) {
        return storage[checkArray(uuid)];
    }

    public void delete(String uuid) {
        storage[checkArray(uuid)] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    private int checkArray(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            } else {
                System.out.println("Error. Resume don't found.");
            }
        }
        return 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
