package ai.dot42.todolist.repository;

import ai.dot42.todolist.repository.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoRepositoryTest {

    private final TodoRepository sut = new TodoRepositoryImpl();

    @AfterEach
    public void cleanUp() {
        sut.deleteAll();
    }

    @Test
    public void saveTodoItem() {
        Todo todo = new Todo();

        Todo result = sut.save(todo);

        assertThat(result).isEqualTo(todo);
        assertThat(sut.countAll()).isEqualTo(1);
    }

    @Test
    public void retrieveTodoItem() {
        Todo todo = new Todo();
        sut.save(todo);

        List<Todo> result = sut.getAll();

        assertThat(result).containsExactly(todo);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void filterTodoItem() {
        List<Todo> todos = List.of(
                new Todo("todo 1", "description 1"),
                new Todo("todo 2", "description 2"),
                new Todo("todo 3", "description 3")
        );

        sut.saveAll(todos);

        List<Todo> result = sut.findByTitleContaining("1");

        assertThat(result).containsExactly(todos.get(0));
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void deleteTodoItem() {
        List<Todo> todos = List.of(
                new Todo(1, "todo 1", "todo 1"),
                new Todo(2, "todo 2", "todo 2"),
                new Todo(3, "todo 3", "todo 3")
        );

        sut.saveAll(todos);
        sut.deleteById(1);

        List<Todo> result = sut.getAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).doesNotContain(todos.get(0));
    }
}