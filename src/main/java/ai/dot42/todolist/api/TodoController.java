package ai.dot42.todolist.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    //TODO 할일 목록을 추가하거나 삭제하는 API도 추가 구현
}
