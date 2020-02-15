package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        addElement(resume, getExistedStorageKey(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        setElement(resume, getNotExistedStorageKey(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return getElement(getNotExistedStorageKey(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteElement(getNotExistedStorageKey(uuid));
    }

    private Integer getNotExistedStorageKey(String uuid) {
        Integer storageKey = getKey(uuid);
        if (!existKey(storageKey)) {
            throw new NotExistStorageException(uuid);
        }
        return storageKey;
    }

    private Integer getExistedStorageKey(String uuid) {
        Integer storageKey = getKey(uuid);
        if (existKey(storageKey)) {
            throw new ExistStorageException(uuid);
        }
        return storageKey;
    }

    protected abstract Integer getKey(String uuid);

    protected abstract boolean existKey(Integer storageKey);

    protected abstract void addElement(Resume resume, Integer index);

    protected abstract void setElement(Resume resume, Integer index);

    protected abstract Resume getElement(Integer index);

    protected abstract void deleteElement(Integer index);
}
