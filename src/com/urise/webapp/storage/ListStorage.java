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
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (uuid.equals(arrayList.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void addElement(Resume resume, int index) {
        arrayList.add(resume);
    }

    @Override
    protected void setElement(Resume resume, int index) {
        arrayList.set(index, resume);
    }

    @Override
    protected Resume getElement(int index) {
        return arrayList.get(index);
    }

    @Override
    protected void deleteElement(int index) {
        arrayList.remove(index);
    }
}
