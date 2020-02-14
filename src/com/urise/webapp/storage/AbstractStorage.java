package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index >= 0 ) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            addElement(resume, index);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            setElement(resume, index);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getElement(index);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteElement(index);
        }
    }

    public abstract int size();

    public abstract Resume[] getAll();

    public abstract void clear();

    protected abstract int getIndex(String uuid);

    protected abstract void addElement(Resume resume, int index);

    protected abstract void setElement(Resume resume, int index);

    protected abstract Resume getElement(int index);

    protected abstract void deleteElement(int index);
}
