package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Integer index = getIndex(resume.getUuid());
        if (index != null) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            int i = -1;
            addElement(resume, i);
        }
    }

    @Override
    public void update(Resume resume) {
        setElement(resume, notExistException(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return getElement(notExistException(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteElement(notExistException(uuid));
    }

    private int notExistException(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract Integer getIndex(String uuid);

    protected abstract void addElement(Resume resume, int index);

    protected abstract void setElement(Resume resume, int index);

    protected abstract Resume getElement(int index);

    protected abstract void deleteElement(int index);
}
