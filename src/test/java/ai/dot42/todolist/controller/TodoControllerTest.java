package ai.dot42.todolist.controller;

import ai.dot42.todolist.repository.Todo;
import ai.dot42.todolist.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void getTodosShouldReturnTodoList() throws Exception {
        List<Integer> expectedTodoIdList = List.of(1, 2, 3);
        Mockito.when(todoService.getTodoIds()).thenReturn(expectedTodoIdList);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos")) // replace with correct URI
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode", is("SUCCESS")))
                .andExpect(jsonPath("$.data", hasSize(expectedTodoIdList.size())));
    }

    @Test
    public void getTodoWithIdShouldReturnTodoDetail() throws Exception {
        int idToFind = 1;
        Todo expectedTodo = new Todo(1, "title", "description");

        Mockito.when(todoService.getTodo(anyInt())).thenReturn(expectedTodo);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{id}", idToFind)) // replace with correct URI
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(expectedTodo.getId())))
                .andExpect(jsonPath("$.data.title", is(expectedTodo.getTitle())))
                .andExpect(jsonPath("$.data.description", is(expectedTodo.getDescription())));
    }

    @Test
    public void createTodoShouldReturnCreatedTodo() throws Exception {
        TodoCreateRequest request = new TodoCreateRequest("new title", "new description");
        Todo createdTodo = new Todo(1, "new title", "new description");

        Mockito.when(todoService.createTodo(any(String.class), any(String.class))).thenReturn(createdTodo);

        mockMvc.perform(MockMvcRequestBuilders.post("/todos") // replace with correct URI
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(createdTodo.getId())))
                .andExpect(jsonPath("$.data.title", is(createdTodo.getTitle())))
                .andExpect(jsonPath("$.data.description", is(createdTodo.getDescription())));
    }

    @Test
    public void updateTodoShouldReturnUpdatedTodo() throws Exception {
        int idToUpdate = 1;
        TodoUpdateRequest request = new TodoUpdateRequest(idToUpdate, "updated title", "updated description");
        Todo updatedTodo = new Todo(idToUpdate, request.getTitle(), request.getDescription());

        Mockito.when(todoService.updateTodo(any(Todo.class))).thenReturn(updatedTodo);

        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}", idToUpdate) // replace with correct URI
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode", is("SUCCESS")))
                .andExpect(jsonPath("$.data.id", is(updatedTodo.getId())))
                .andExpect(jsonPath("$.data.title", is(updatedTodo.getTitle())))
                .andExpect(jsonPath("$.data.description", is(updatedTodo.getDescription())));
    }

    @Test
    public void deleteTodoShouldReturnSuccess() throws Exception {
        int idToDelete = 1;

        Mockito.doNothing().when(todoService).deleteTodoById(idToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", idToDelete)) // replace with correct URI
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnCode", is("SUCCESS")));
    }
}