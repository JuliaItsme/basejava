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

    public void update(Resume resume) {
        if (checkArray(resume)) {
            storage[i] = resume;
        } else {
            System.out.println("Error. Resume don't found.");
        }
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Error. The array is full.");
        } else if (!checkArray(resume)) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Error. There is such resume already.");
        }
    }

    private boolean checkArray(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return true;
            }
        }
        return false;
    }

    public Resume get(String uuid) {
        if (checkGet(uuid)) {
            return storage[i];
        }
        return null;
    }

    public void delete(String uuid) {
        if (checkGet(uuid)) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Error. Resume don't found.");
        }
    }

    public boolean checkGet(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
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