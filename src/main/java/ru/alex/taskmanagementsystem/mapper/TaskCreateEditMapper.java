package ru.alex.taskmanagementsystem.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.task.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.entity.Priority;
import ru.alex.taskmanagementsystem.entity.Status;
import ru.alex.taskmanagementsystem.entity.Task;
import ru.alex.taskmanagementsystem.repository.UserRepository;

import java.util.Optional;

@Component
public class TaskCreateEditMapper implements Mapper<Task, TaskCreateEditDto> {
    private final UserRepository userRepository;

    @Autowired
    public TaskCreateEditMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Task toEntity(TaskCreateEditDto dto) {
        Task task = new Task();

        Optional.ofNullable(dto.executorId()).ifPresent(executorId ->
                task.setExecutor(userRepository.findById(executorId)
                        .orElseThrow(() -> new EntityNotFoundException("executor with id " + executorId + " not found"))));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        Optional.ofNullable(dto.status())
                        .ifPresent(status -> task.setStatus(Status.values()[status]));
        Optional.ofNullable(dto.priority())
                        .ifPresent(priority -> task.setPriority(Priority.values()[priority]));

        return task;
    }
}