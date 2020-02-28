package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected void doSave(Resume resum, Object resume) {
        map.put(resum.getUuid(), resum);
    }

    @Override
    protected void doUpdate(Resume resum, Object resume) {
        map.put(resum.getUuid(), resum);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected void doDelete(Object resume) {
        map.remove(((Resume) resume).getUuid());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }
}
