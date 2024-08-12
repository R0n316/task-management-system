package ru.alex.taskmanagementsystem.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.taskmanagementsystem.dto.PageResponse;
import ru.alex.taskmanagementsystem.dto.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.dto.TaskReadDto;
import ru.alex.taskmanagementsystem.entity.Status;
import ru.alex.taskmanagementsystem.service.TaskService;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public PageResponse<TaskReadDto> findAll(Pageable pageable) {
        Page<TaskReadDto> tasks = taskService.findAll(pageable);
        return PageResponse.of(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskReadDto> create(@RequestBody TaskCreateEditDto task) {
        return new ResponseEntity<>(taskService.create(task), CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskReadDto> update(@PathVariable("id") Integer id,
                                              @RequestBody TaskCreateEditDto task){
        return new ResponseEntity<>(taskService.update(id,task),OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        taskService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PatchMapping("/{id}/edit-status")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable("id") Integer id,
                                                   @RequestBody Map<String, Status> status){
        if(status.isEmpty() || !status.containsKey("status")) {
            throw new ResponseStatusException(BAD_REQUEST);
        }
        taskService.editStatus(id,status.get("status"));
        return new ResponseEntity<>(OK);
    }
}
