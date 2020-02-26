package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> hashMap = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object storageKey) {
        return hashMap.containsKey((String) storageKey);
    }

    @Override
    protected void doSave(Resume resume, Object storageKey) {
        hashMap.put((String) storageKey, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object storageKey) {
        hashMap.put((String) storageKey, resume);
    }

    @Override
    protected Resume doGet(Object storageKey) {
        return hashMap.get((String) storageKey);
    }

    @Override
    protected void doDelete(Object storageKey) {
        hashMap.remove((String) storageKey);
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> arrayList = new ArrayList<>(hashMap.values());
        Collections.sort(arrayList, (o1, o2) -> o1.toString().compareTo(o2.toString()));
        return arrayList;
    }

    @Override
    public void clear() {
        hashMap.clear();
    }
}
