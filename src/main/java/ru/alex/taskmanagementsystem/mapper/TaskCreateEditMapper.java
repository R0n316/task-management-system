package ru.alex.taskmanagementsystem.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.entity.Priority;
import ru.alex.taskmanagementsystem.entity.Status;
import ru.alex.taskmanagementsystem.entity.Task;
import ru.alex.taskmanagementsystem.repository.UserRepository;

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
        // TODO потом надо поменять чтобы автор проставлялся из spring security контекста
        task.setAuthor(userRepository.findById(dto.authorId())
                .orElseThrow(() -> new EntityNotFoundException("author not found")));

        task.setExecutor(userRepository.findById(dto.executorId())
                .orElseThrow(() -> new EntityNotFoundException("executor not found")));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(Status.values()[dto.status()]);
        task.setPriority(Priority.values()[dto.priority()]);

        return task;
    }
}