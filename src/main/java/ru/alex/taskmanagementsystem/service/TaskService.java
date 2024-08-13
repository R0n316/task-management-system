package ru.alex.taskmanagementsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.taskmanagementsystem.dto.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.dto.TaskFilter;
import ru.alex.taskmanagementsystem.dto.TaskReadDto;
import ru.alex.taskmanagementsystem.entity.Status;
import ru.alex.taskmanagementsystem.entity.Task;
import ru.alex.taskmanagementsystem.mapper.TaskCreateEditMapper;
import ru.alex.taskmanagementsystem.mapper.TaskReadMapper;
import ru.alex.taskmanagementsystem.repository.TaskRepository;
import ru.alex.taskmanagementsystem.repository.UserRepository;
import ru.alex.taskmanagementsystem.util.JwtUtil;

import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskReadMapper taskReadMapper;
    private final TaskCreateEditMapper taskCreateEditMapper;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository,
                       TaskReadMapper taskReadMapper,
                       TaskCreateEditMapper taskCreateEditMapper,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskReadMapper = taskReadMapper;
        this.taskCreateEditMapper = taskCreateEditMapper;
        this.userRepository = userRepository;
    }

    public TaskReadDto create(TaskCreateEditDto taskDto) {
        Task task = taskCreateEditMapper.toEntity(taskDto);

        String email = JwtUtil.getEmailByJwtToken();
        task.setAuthor(userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("author not found")));

        return taskReadMapper.toDto(taskRepository.save(task));
    }

    public Page<TaskReadDto> findByFilter(TaskFilter filter, Pageable pageable) {
        return taskRepository.findAllByFilter(filter,pageable)
                .map(taskReadMapper::toDto);
    }

    public TaskReadDto update(Integer id, TaskCreateEditDto taskDto) {
        Optional<Task> foundTask = taskRepository.findById(id);
        checkTaskAuthorAndAuthorizedUser(foundTask);
        Task updatedTask = foundTask
                .map(task -> patchUpdate(task, taskCreateEditMapper.toEntity(taskDto)))
                .orElseThrow(EntityNotFoundException::new);
        return taskReadMapper.toDto(taskRepository.save(updatedTask));
    }

    public void delete(Integer id) {
        Optional<Task> foundTask = taskRepository.findById(id);
        checkTaskAuthorAndAuthorizedUser(foundTask);
        taskRepository.deleteById(id);
    }

    public void editStatus(Integer id, Status status) {
        Optional<Task> foundTask = taskRepository.findById(id);
        checkTaskAuthorAndExecutorAndAuthorizedUser(foundTask);
        foundTask.ifPresent(task -> {
            task.setStatus(status);
            taskRepository.save(task);
        });
    }

    private Task patchUpdate(Task task, Task updatedTask) {
        ReflectionUtils.doWithFields(Task.class, field -> {
            field.setAccessible(true);
            Object updatedValue = ReflectionUtils.getField(field, updatedTask);
            if (updatedValue != null) {
                ReflectionUtils.setField(field, task, updatedValue);
            }
        });
        return task;
    }

    private void checkTaskAuthorAndAuthorizedUser(Optional<Task> foundTask) {
        userRepository.findByEmail(JwtUtil.getEmailByJwtToken()).ifPresent(user -> {
            if (foundTask.isEmpty()) {
                throw new ResponseStatusException(NOT_FOUND);
            } else {
                if (!foundTask.get().getAuthor().getId().equals(user.getId())) {
                    throw new ResponseStatusException(FORBIDDEN);
                }
            }
        });
    }

    private void checkTaskAuthorAndExecutorAndAuthorizedUser(Optional<Task> foundTask) {
        userRepository.findByEmail(JwtUtil.getEmailByJwtToken()).ifPresent(user -> {
            if (foundTask.isEmpty()) {
                throw new ResponseStatusException(NOT_FOUND);
            } else {
                Task task = foundTask.get();
                if (!task.getAuthor().getId().equals(user.getId()) && !task.getExecutor().getId().equals(user.getId())) {
                    throw new ResponseStatusException(FORBIDDEN);
                }
            }
        });
    }
}
