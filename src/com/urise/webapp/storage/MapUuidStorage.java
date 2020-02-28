package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object uuid) {
        return map.containsKey((String) uuid);
    }

    @Override
    protected void doSave(Resume resume, Object uuid) {
        map.put((String) uuid, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object uuid) {
        map.put((String) uuid, resume);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return map.get((String) uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected void doDelete(Object uuid) {
        map.remove((String) uuid);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }
}
