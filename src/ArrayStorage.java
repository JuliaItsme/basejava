import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    void clear() {

        Arrays.fill(storage, 0, size, null);
    }

    void save(Resume resume) {
        Arrays.sort(storage);
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                size++;
            }
        }
        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int j;
        for (j = 0; j < size; j++) {
            if (storage[j].uuid == uuid)
                break;
        }
        for (int k = j; k < size + 1; k++) {
            storage[k] = storage[k + 1];
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}