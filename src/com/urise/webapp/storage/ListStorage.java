package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> arrayList = new ArrayList<>();

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public Resume[] getAll() {
        return arrayList.toArray(new Resume[arrayList.size()]);
    }

    @Override
    public void clear() {
        arrayList.clear();
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (uuid.equals(arrayList.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean existKey(Integer storageKey) {
        return storageKey != null;
    }

    @Override
    protected void addElement(Resume resume, Integer storageKey) {
        arrayList.add(resume);
    }

    @Override
    protected void setElement(Resume resume, Integer storageKey) {
        arrayList.set(storageKey, resume);
    }

    @Override
    protected Resume getElement(Integer storageKey) {
        return arrayList.get(storageKey);
    }

    @Override
    protected void deleteElement(Integer storageKey) {
        arrayList.remove(storageKey.intValue());
    }
}
