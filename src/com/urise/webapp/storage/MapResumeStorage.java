package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage  extends AbstractStorage{
    Map<String, Resume> hashMap = new HashMap<>();

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object resumeKey) {
        return hashMap.containsKey(((Resume)resumeKey).getUuid());
    }

    @Override
    protected void doSave(Resume resume, Object resumeKey) {
        hashMap.put(((Resume)resumeKey).getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object resumeKey) {
        hashMap.put(((Resume)resumeKey).getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object resumeKey) {
        return hashMap.get(((Resume)resumeKey).getUuid());
    }

    @Override
    protected void doDelete(Object resumeKey) {
        hashMap.remove(((Resume)resumeKey).getUuid());
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
