package ai.dot42.todolist.service;

import ai.dot42.todolist.repository.Todo;
import ai.dot42.todolist.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private final Todo todo = new Todo(
            1,
            "Test Todo",
            "This is a test todo"
    );

    public TodoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        clearInvocations(todoRepository);
    }

    @Test
    public void createTodo() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo createdTodo = todoService.createTodo(todo.getTitle(), todo.getDescription());

        assertThat(createdTodo).isEqualTo(todo);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    public void getTodoList() {
        when(todoRepository.getAll()).thenReturn(List.of(todo, todo));

        List<Integer> todoList = todoService.getTodoList();

        assertThat(todoList).containsExactly(todo.getId(), todo.getId());
        verify(todoRepository, times(1)).getAll();
    }

    @Test
    public void updateTodo() {
        Todo updatedTodo = new Todo(
                1,
                "Updated Test Todo",
                "This is an updated test todo"
        );

        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        Todo result = todoService.updateTodo(updatedTodo);

        assertThat(result).isEqualTo(updatedTodo);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    public void deleteTodo() {
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        todoService.createTodo(todo.getTitle(), todo.getDescription());

        doNothing().when(todoRepository).deleteById(anyInt());
        todoService.deleteTodoById(todo.getId());

        when(todoRepository.getAll()).thenReturn(List.of());
        List<Integer> todoList = todoService.getTodoIds();

        assertThat(todoList).doesNotContain(todo.getId());
        verify(todoRepository, times(1)).deleteById(anyInt());
        verify(todoRepository, times(1)).getAll();
    }
}