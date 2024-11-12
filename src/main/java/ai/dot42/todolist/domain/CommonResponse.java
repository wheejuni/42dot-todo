package ai.dot42.todolist.domain;

import lombok.Data;

@Data
public class CommonResponse<T> {
    private String returnCode;
    private T data;
}
