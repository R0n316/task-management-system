package ru.alex.taskmanagementsystem.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.comment.CommentReadDto;
import ru.alex.taskmanagementsystem.dto.task.TaskReadDto;
import ru.alex.taskmanagementsystem.entity.Task;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TaskReadMapper implements Mapper<Task, TaskReadDto> {
    private final UserReadMapper userReadMapper;
    private final CommentReadMapper commentReadMapper;
    @Autowired
    public TaskReadMapper(UserReadMapper userReadMapper,
                          CommentReadMapper commentReadMapper) {
        this.userReadMapper = userReadMapper;
        this.commentReadMapper = commentReadMapper;
    }

    @Override
    public TaskReadDto toDto(Task entity) {

        List<CommentReadDto> comments = Optional.ofNullable(entity.getComments())
                .orElse(Collections.emptyList())
                .stream()
                .map(commentReadMapper::toDto)
                .toList();

        return new TaskReadDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus().name().toLowerCase().replace("_", " "),
                entity.getPriority().name().toLowerCase(),
                userReadMapper.toDto(entity.getAuthor()),
                userReadMapper.toDto(entity.getExecutor()),
                comments
        );
    }
}
