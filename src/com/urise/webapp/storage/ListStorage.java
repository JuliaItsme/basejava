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
    protected boolean existKey(Object storageKey) {
        return storageKey != null;
    }

    @Override
    protected void saveElement(Resume resume, Object storageKey) {
        arrayList.add(resume);
    }

    @Override
    protected void updateElement(Resume resume, Object storageKey) {
        arrayList.set((Integer) storageKey, resume);
    }

    @Override
    protected Resume getElement(Object storageKey) {
        return arrayList.get((Integer)storageKey);
    }

    @Override
    protected void deleteElement(Object storageKey) {
        arrayList.remove(((Integer)storageKey).intValue());
    }
}
