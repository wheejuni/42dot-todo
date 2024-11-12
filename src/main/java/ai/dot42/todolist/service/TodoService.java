package ai.dot42.todolist.service;

import ai.dot42.todolist.repository.Todo;

import java.util.List;

public interface TodoService {
    Todo getTodo(Integer id);
    List<Integer> getTodoIds();
    List<Todo> getList();
    Todo findById(Integer id);
    void deleteTodoById(Integer id);
    Todo createTodo(String title, String description);
    Todo updateTodo(Todo toBeUpdated);
}
