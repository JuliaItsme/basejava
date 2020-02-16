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

    //Collection<V> values(): возвращает набор всех значений отображения
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
    protected Integer getKey(String uuid) {
        for (int i = 0; i < hashMap.size(); i++) {
            if (uuid.equals(hashMap.get(uuid))){
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean existKey(Object storageKey) {
        return hashMap.get(storageKey) != null;
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
        return hashMap.get(storageKey);
    }

    @Override
    protected void deleteElement(Object storageKey) {
        hashMap.remove(storageKey);
    }
}
