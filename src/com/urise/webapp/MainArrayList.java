package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;

public class MainArrayList {
    static final ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1");
        final Resume r2 = new Resume("uuid2");
        final Resume r3 = new Resume("uuid3");

        LIST_STORAGE.save(r1);
        LIST_STORAGE.save(r2);
        LIST_STORAGE.save(r3);

        LIST_STORAGE.update(r1);

        System.out.println("Get r1: " + LIST_STORAGE.get(r1.getUuid()) + "\n");
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size() + "\n");
        // System.out.println("Get dummy: " + LIST_STORAGE.get("dummy") + "\n");

        LIST_STORAGE.delete(r1.getUuid());
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size() + "\n");

        LIST_STORAGE.clear();
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size() + "\n");
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : LIST_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
