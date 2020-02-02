package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.SortedArrayStorage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static final SortedArrayStorage ARRAY_STO = new SortedArrayStorage();

    public static void main(String[] args) {

        final Resume r1 = new Resume("uuid1");
        final Resume r2 = new Resume("uuid2");
        final Resume r3 = new Resume("uuid3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        ARRAY_STORAGE.update(r1);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()) + "\n");
        System.out.println("Size: " + ARRAY_STORAGE.size() + "\n");
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy") + "\n");

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STO.save(r1);
        ARRAY_STO.save(r2);
        ARRAY_STO.save(r3);

        ARRAY_STO.update(r2);

        printAllTwo();
        ARRAY_STO.delete(r2.getUuid());
        printAllTwo();


        System.out.println("Get r1: " + ARRAY_STO.get(r2.getUuid()));

        ARRAY_STO.clear();
        printAllTwo();

        System.out.println("Size: " + ARRAY_STO.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }

    static void printAllTwo() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STO.getAll()) {
            System.out.println(r);
        }
    }
}