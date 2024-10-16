package ai.dot42.todolist.domain;

import java.util.List;
import java.util.Map;

public abstract class BaseRepository<T, ID> {

    private Map<ID, T> entry;

    public T save(T entity, ID id) {
        return entry.put(id, entity);
    }

    public T findById(ID id) {
        return entry.get(id);
    }
}
