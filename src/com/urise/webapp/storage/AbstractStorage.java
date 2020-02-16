package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        saveElement(resume, getExistedStorageKey(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        updateElement(resume, getNotExistedStorageKey(resume.getUuid()));
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

    protected abstract boolean existKey(Object storageKey);

    protected abstract void saveElement(Resume resume, Object storageKey);

    protected abstract void updateElement(Resume resume, Object storageKey);

    protected abstract Resume getElement(Object storageKey);

    protected abstract void deleteElement(Object storageKey);
}