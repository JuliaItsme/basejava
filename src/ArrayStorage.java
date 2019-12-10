import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, 0, size(), null);
    }

    void save(Resume resume) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = resume;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i <= size(); i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int j;
        for (j = 0; j < storage.length; j++) {
            if (storage[j].uuid == uuid)
                break;
        }
        for (int k = j; k < storage.length - 1; k++) {
            storage[k] = storage[k + 1];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                count++;
            }
        }
        Arrays.sort(storage);
        return count;
    }
}
