package ru.alex.taskmanagementsystem.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.TaskReadDto;
import ru.alex.taskmanagementsystem.entity.Task;

@Component
public class TaskReadMapper implements Mapper<Task, TaskReadDto> {
    private final UserReadMapper userReadMapper;

    @Autowired
    public TaskReadMapper(UserReadMapper userReadMapper) {
        this.userReadMapper = userReadMapper;
    }

    @Override
    public TaskReadDto toDto(Task entity) {
        return new TaskReadDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus().name().toLowerCase().replace("_", " "),
                entity.getPriority().name().toLowerCase(),
                userReadMapper.toDto(entity.getAuthor()),
                userReadMapper.toDto(entity.getExecutor())
        );
    }
}
