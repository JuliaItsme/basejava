package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    protected String getSearchKey(String uuidKey) {
        return uuidKey;
    }

    @Override
    protected boolean isExist(Object uuidKey) {
        return hashMap.containsKey((String) uuidKey);
    }

    @Override
    protected void doSave(Resume resume, Object uuidKey) {
        hashMap.put((String) uuidKey, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object uuidKey) {
        hashMap.put((String) uuidKey, resume);
    }

    @Override
    protected Resume doGet(Object uuidKey) {
        return hashMap.get((String) uuidKey);
    }

    @Override
    protected List<Resume> doGetAll() {
        return new ArrayList<>(hashMap.values());
    }

    @Override
    protected void doDelete(Object uuidKey) {
        hashMap.remove((String) uuidKey);
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public void clear() {
        hashMap.clear();
    }
}
