package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;
import com.urise.webapp.storage.MapResumeStorage;
import com.urise.webapp.storage.MapUuidStorage;

public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static final MapResumeStorage MAP_RESUME = new MapResumeStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1", "Name1");
        final Resume r2 = new Resume("uuid2", "Name2");
        final Resume r3 = new Resume("uuid3", "Name3");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        MAP_RESUME.save(r1);
        MAP_RESUME.save(r2);
        MAP_RESUME.save(r3);
        MAP_RESUME.get(r1.getUuid());
        MAP_RESUME.update(r2);
        MAP_RESUME.delete(r1.getUuid());


        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size() + "\n");

        ARRAY_STORAGE.update(r1);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy") + "\n");

        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        //System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));

        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}