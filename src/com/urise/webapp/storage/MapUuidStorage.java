package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    Map<String, Resume> hashMap = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object uuid) {
        return hashMap.containsKey((String) uuid);
    }

    @Override
    protected void doSave(Resume resume, Object uuid) {
        hashMap.put((String) uuid, resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object uuid) {
        hashMap.put((String) uuid, resume);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return hashMap.get((String) uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        hashMap.remove((String) uuid);
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
