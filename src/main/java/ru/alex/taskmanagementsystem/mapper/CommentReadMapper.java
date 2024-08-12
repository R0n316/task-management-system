package ru.alex.taskmanagementsystem.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.CommentReadDto;
import ru.alex.taskmanagementsystem.dto.TaskReadDto;
import ru.alex.taskmanagementsystem.dto.UserReadDto;
import ru.alex.taskmanagementsystem.entity.Comment;

import java.util.Optional;

@Component
public class CommentReadMapper implements Mapper<Comment, CommentReadDto> {
    private final TaskReadMapper taskReadMapper;
    private final UserReadMapper userReadMapper;

    @Autowired
    public CommentReadMapper(TaskReadMapper taskReadMapper,
                             UserReadMapper userReadMapper) {
        this.taskReadMapper = taskReadMapper;
        this.userReadMapper = userReadMapper;
    }

    @Override
    public CommentReadDto toDto(Comment entity) {
        UserReadDto user = Optional.ofNullable(entity.getUser())
                .map(userReadMapper::toDto)
                .orElse(null);

        TaskReadDto task = Optional.ofNullable(entity.getTask())
                .map(taskReadMapper::toDto)
                .orElse(null);

        return new CommentReadDto(
                entity.getId(),
                user,
                task,
                entity.getText()
        );
    }
}
