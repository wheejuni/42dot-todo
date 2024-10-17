package ai.dot42.todolist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class TodolistApiAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnFiveItemsInResponse() throws Exception {
        mockMvc.perform(get("/api/v1/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items.length()").value(5));
    }

    @Test
    public void shouldReturnTodoId() throws Exception {
        final String contentAsString = mockMvc.perform(post("/api/v1/todo"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        final Map<String, Object> responseMap = objectMapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {
        });

        assertNotNull(responseMap);
        assertNotNull(responseMap.get("id"));
    }

    @Test
    public void shouldReturnTodoItemThatIsMatchedId() throws Exception {
        final String expectedTodoId = "expected-id";
        final String contentAsString = mockMvc.perform(get("/api/v1/todo/{id}", expectedTodoId))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        final Map<String, Object> responseMap = objectMapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {
        });

        assertNotNull(responseMap);
        assertNotNull(responseMap.get("todo"));
        final Todo todo = responseMap.get("todo");
        assertEquals(expectedTodoId, todo.getId());
    }
}
