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
        String uuid = resume.getUuid();
        if (size == storage.length) {
            System.out.println("Error. The array is full.");
        } else if (findIndex(uuid) == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Error. There is such resume already.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Error. Resume don't found.");
        return null;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = resume;
        } else {
            System.out.println("Error. Resume don't found.");
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Error. Resume don't found.");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}