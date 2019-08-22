package ru.babaninnv.test.mts.rest.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

import ru.babaninnv.test.mts.rest.domain.Task;
import ru.babaninnv.test.mts.rest.exceptions.TaskNotFoundException;
import ru.babaninnv.test.mts.rest.service.TaskService;

/**
 * @author Nikita Babanin
 * @version 1.0
 */
@RestController
@RequestMapping("/task")
@Validated
public class TaskRestController {

    private static final String GUID_REGEXP = "(\\{){0,1}[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}(}){0,1}";

    private final TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<String> task() {
        String guid = taskService.add();
        return new ResponseEntity<>(guid, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{guid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TaskResource> getTaskById(@PathVariable("guid") @Pattern(regexp = GUID_REGEXP) String guid)
            throws TaskNotFoundException {
        Task task = taskService.getById(guid).orElseThrow(() -> new TaskNotFoundException(guid));
        return new ResponseEntity<>(new TaskResource(task.getStatus(), task.getTimestamp()), HttpStatus.OK);
    }
}
