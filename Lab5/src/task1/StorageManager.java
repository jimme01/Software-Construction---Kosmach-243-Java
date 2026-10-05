package task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/** Узагальнене сховище елементів типу T. */
public class StorageManager<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    /** Пошук першого елемента за критерієм. */
    public Optional<T> find(Predicate<T> criterion) {
        for (T item : items) {
            if (criterion.test(item)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /** Фільтрація звичайним циклом; результат — колекція лише для читання. */
    public List<T> filter(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return Collections.unmodifiableList(result);
    }
}
