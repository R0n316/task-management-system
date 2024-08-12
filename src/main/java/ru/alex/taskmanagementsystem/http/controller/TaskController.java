package ru.alex.taskmanagementsystem.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.taskmanagementsystem.dto.PageResponse;
import ru.alex.taskmanagementsystem.dto.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.dto.TaskReadDto;
import ru.alex.taskmanagementsystem.service.TaskService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity<TaskReadDto> update(@PathVariable Integer id,
                                              @RequestBody TaskCreateEditDto task){
        return new ResponseEntity<>(taskService.update(id,task),OK);
    }
}
