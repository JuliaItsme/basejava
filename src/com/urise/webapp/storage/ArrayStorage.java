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
    }

    public void update(Resume resume) {
        //TODO check if resume present
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == resume.getUuid()) {
                storage[i] = resume;
                counter++;
                break;
            }
        }
        if(counter == 0) {
            System.out.println("Error");
        }
    }


    public void save(Resume resume) {
        //TODO check if resume not present
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == resume.getUuid()) {
                System.out.println("Error");
                counter++;
                break;
            }
        }
        if(counter == 0) {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        //TODO check if resume present
        int counter = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid() == uuid) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                counter++;
                size--;
            }
        }
        if(counter == 0) {
            System.out.println("Error");
        }
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