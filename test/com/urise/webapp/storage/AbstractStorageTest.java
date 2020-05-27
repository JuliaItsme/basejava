package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();
    protected Storage storage;

    private static final String UUID_1;
    private static final String UUID_2;
    private static final String UUID_3;
    private static final String UUID_4;

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        UUID_1 = ResumeTestData.getUuid1();
        UUID_2 = ResumeTestData.getUuid2();
        UUID_3 = ResumeTestData.getUuid3();
        UUID_4 = ResumeTestData.getUuid4();
        RESUME_1 = ResumeTestData.getResume1();
        RESUME_2 = ResumeTestData.getResume2();
        RESUME_3 = ResumeTestData.getResume3();
        RESUME_4 = ResumeTestData.getResume4();
    }

    protected AbstractStorageTest(Storage storage) {
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
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        for (Resume resume: list) {
            System.out.println(resume.getContacts());
            System.out.println(resume.getSections());
        }
        assertEquals(3, list.size());
        assertEquals(list, storage.getAllSorted());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2, "New Name");
        RESUME_2.addContact(ContactType.LINKEDIN, "mail1@google.com");
        RESUME_2.addContact(ContactType.SKYPE, "NewSkype");
        RESUME_2.addContact(ContactType.PHONE_NUMBER, "+7 921 222-22-22");
        storage.update(resume);
        System.out.println(RESUME_2.getContact(ContactType.SKYPE));
        assertTrue(resume.equals(storage.get(UUID_2)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("uuid6");
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);

        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}