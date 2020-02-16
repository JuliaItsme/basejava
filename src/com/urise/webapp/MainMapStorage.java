package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapStorage;


public class MainMapStorage {
    static final MapStorage MAP_STORAGE = new MapStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1");
        final Resume r2 = new Resume("uuid2");
        final Resume r3 = new Resume("uuid3");

        MAP_STORAGE.save(r1);
        MAP_STORAGE.save(r2);
        MAP_STORAGE.save(r3);

        printAll();
        System.out.println("Size: " + MAP_STORAGE.size() + "\n");

        MAP_STORAGE.update(r1);

        System.out.println("Get r1: " + MAP_STORAGE.get(r1.getUuid()));
        // System.out.println("Get dummy: " + LIST_STORAGE.get("dummy") + "\n");

        MAP_STORAGE.delete(r1.getUuid());
        printAll();
        System.out.println("Size: " + MAP_STORAGE.size() + "\n");
        // System.out.println("Get r1: " + LIST_STORAGE.get(r1.getUuid()));

        MAP_STORAGE.clear();
        printAll();
        System.out.println("Size: " + MAP_STORAGE.size() + "\n");
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : MAP_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
