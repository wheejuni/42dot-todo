package ai.dot42.todolist.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TodoServiceTest {
    @InjectMocks
    private TodoService todoService;

    @Test
    public void shouldReturnTodoList() throws Exception {
        final List<Todo> todoList = todoService.getList();
        assertNotNull(todoList);
        assertEquals(5, todoList.size());
    }

    @Test
    public void shouldReturnTodoItem() throws Exception {
        final String expectedTodoId = "expected-id";
        final Todo todo = todoService.findById(expectedTodoId);
        assertNotNull(todo);
        assertEquals(expectedTodoId, todo.getId());
    }

    @Test
    public void shouldCreateTodoItem() throws Exception {
        final String expectedTitle = "title";
        final String expectedContent = "content";
        final Todo todo = todoService.create(expectedTitle, expectedContent);
        assertNotNull(todo);
        assertEquals(expectedTitle, createdTodo.getTitle());
        assertEquals(expectedContent, createdTodo.getContent());
    }
}
