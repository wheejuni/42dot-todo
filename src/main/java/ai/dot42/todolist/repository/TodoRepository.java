package ai.dot42.todolist.repository;

import java.util.List;

public interface TodoRepository {
    Todo save(Todo todo);

    int countAll();

    List<Todo> getAll();

    List<Todo> saveAll(List<Todo> todos);

    void deleteById(Integer id);

    void deleteAll();

    List<Todo> findByTitleContaining(String title);
}
