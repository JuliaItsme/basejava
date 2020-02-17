package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> hashMap = new HashMap<>();

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public Resume[] getAll() {
        List<Resume> arrayList = new ArrayList<>(hashMap.values());
        return arrayList.toArray(new Resume[arrayList.size()]);
    }

    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean existKey(Object storageKey) {
        return hashMap.containsKey((String) storageKey);
    }

    @Override
    protected void saveElement(Resume resume, Object storageKey) {
        hashMap.put((String) storageKey, resume);
    }

    @Override
    protected void updateElement(Resume resume, Object storageKey) {
        hashMap.put((String) storageKey, resume);
    }

    @Override
    protected Resume getElement(Object storageKey) {
        return hashMap.get((String) storageKey);
    }

    @Override
    protected void deleteElement(Object storageKey) {
        hashMap.remove((String) storageKey);
    }
}
