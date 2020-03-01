package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        arrayList.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        arrayList.set(searchKey, resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return arrayList.get(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(arrayList);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        arrayList.remove(searchKey.intValue());
    }

    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public void clear() {
        arrayList.clear();
    }
}
