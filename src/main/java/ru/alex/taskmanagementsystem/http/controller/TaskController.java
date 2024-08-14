package ru.alex.taskmanagementsystem.http.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.taskmanagementsystem.dto.PageResponse;
import ru.alex.taskmanagementsystem.dto.PaginationRequest;
import ru.alex.taskmanagementsystem.dto.task.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.dto.task.TaskFilter;
import ru.alex.taskmanagementsystem.dto.task.TaskReadDto;
import ru.alex.taskmanagementsystem.entity.Status;
import ru.alex.taskmanagementsystem.service.TaskService;

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
    public PageResponse<TaskReadDto> findAll(@ModelAttribute TaskFilter filter,
                                             @Valid PaginationRequest request) {
        Page<TaskReadDto> tasks = taskService.findByFilter(
                filter,
                PageRequest.of(request.pageNumber(),request.pageSize())
        );
        return PageResponse.of(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskReadDto> create(@RequestBody @Valid TaskCreateEditDto task) {
        return new ResponseEntity<>(taskService.create(task), CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskReadDto> update(@PathVariable("id") Integer id,
                                              @RequestBody @Valid TaskCreateEditDto task) {
        return new ResponseEntity<>(taskService.update(id, task), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        taskService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @PatchMapping("/{id}/edit-status")
    public ResponseEntity<HttpStatus> changeStatus(@PathVariable("id") Integer id,
                                                   @RequestBody Status status) {
        taskService.editStatus(id, status);
        return new ResponseEntity<>(OK);
    }
}
