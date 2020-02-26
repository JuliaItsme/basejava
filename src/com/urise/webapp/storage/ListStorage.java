package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> arrayList = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (uuid.equals(arrayList.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        arrayList.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        arrayList.set((Integer) searchKey, resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return arrayList.get((Integer)searchKey);
    }

    @Override
    protected void doDelete(Object searchKey) {
        arrayList.remove(((Integer)searchKey).intValue());
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public List<Resume> getAllSorted() {
        return arrayList;
    }

    @Override
    public void clear() {
        arrayList.clear();
    }
}
