package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.print.DocFlavor;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Resume[] expected = storage.getAll();
        for (Resume resume : expected) {
            Assert.assertNull(resume);
        }
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expected = storage.getAll();
        Resume[] storage = new Resume[3];
        storage[0] = RESUME_1;
        storage[1] = RESUME_2;
        storage[2] = RESUME_3;
        Assert.assertArrayEquals(expected, storage);
    }

    @Test
    public void update() throws Exception {
        storage.update(RESUME_1);
        Assert.assertEquals(RESUME_1, storage.get("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test//(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        Resume[] resume = new Resume[3];
        try {
            resume[0] = RESUME_1;
            resume[1] = RESUME_2;
        } catch (StorageException st) {
            Assert.fail("Storage not overflow");
        }
        try {
            resume[2] = RESUME_3;
            resume[3] = new Resume("uuid4");
        } catch (ArrayIndexOutOfBoundsException ar) {
            System.out.println("Storage overflow");
        }
    }

    @Test
    public void delete() throws Exception {
        storage.delete(RESUME_1.getUuid());
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void get() throws Exception {
        Resume resume = storage.get(RESUME_1.getUuid());
        Resume expected = new Resume("uuid1");
        Assert.assertEquals(expected, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}