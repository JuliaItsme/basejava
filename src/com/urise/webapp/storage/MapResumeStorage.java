package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> hashMap = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return hashMap.get(uuid);
    }

    @Override
    protected boolean isExist(Object resumeKey) {
        return hashMap.containsKey(resumeKey);
    }

    @Override
    protected void doSave(Resume resume, Object resumeKey) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object resumeKey) {
        hashMap.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object resumeKey) {
        return (Resume) resumeKey;
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> arrayList = new ArrayList<>(hashMap.values());
        return arrayList;
    }

    @Override
    protected void doDelete(Object resumeKey) {
        hashMap.remove(((Resume) resumeKey).getUuid());
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
