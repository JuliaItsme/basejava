package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("File don't write", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("File don't read", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] file = directory.listFiles();
        if (file == null) {
            throw new StorageException("File don't read", directory.getName());
        }
        List<Resume> list = new ArrayList<>();
        for (File value : file) {
            list.add(doGet(value));
        }
        return list;
    }

    @Override
    protected void doDelete(File file) {
        file.delete();
        if (!file.delete()) {
            throw new StorageException("File don't delete", file.getName());
        }
    }

    @Override
    public int size() {
        String[] str = directory.list();
        if (str== null) {
            throw new StorageException("File not found", directory.getName());
        }
        return str.length;
    }

    @Override
    public void clear() {
        File[] file = directory.listFiles();
        if (file == null) {
            throw new StorageException("File don't read", directory.getName());
        }
        for (File value : file) {
            doDelete(value);
        }
    }
}
